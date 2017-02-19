import database.DBWorker;
import database.Queries;
import entities.Request;
import entities.Response;
import entities.Task;
import entities.User;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientEntity extends Thread {
    private Logger log = Logger.getLogger(ClientEntity.class.getName());
    private Socket socket;
    private Server server;
    private PrintWriter writer;
    private BufferedReader reader;
    private String read;
    JsonParser parser = new JsonParser();
    Queries queries = new Queries();
    DBWorker dbWorker = new DBWorker();


    public ClientEntity(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        start();
    }

    @Override
    public void run() {
        try {
            dbWorker.queryById();
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            while ((read = reader.readLine()) != null) {
                if (read.equals("exit")) {
                    break;
                }
                log.info("Сообщение от клиента: " + read);
                parseRequest();
                final String data = read;
                log.info("Сообщение клиенту: " + read);
                //вызов метода отправки сообщения всем клиентам
                // - желательно в отдельном потоке
                new Thread(new Runnable() {
                    public void run() {
                        server.getClientEntity().writer.println(data);
                        server.getClientEntity().writer.flush();
//                        for (int i = 0; i < server.getClients().size(); i++) {
//                            server.getClients().get(i).writer.println(data);
//                            server.getClients().get(i).writer.flush();
//                        }
                    }
                }).start();
            }
            writer.close();
            server.remove(this);
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseRequest(){
        String request;
        if(parser.parseFromJson(read).getRequest()!=null) {
            request = parser.parseFromJson(read).getRequest();
            if(request.equals(queries.DONE_TASK)){
                doneTask(queries.DONE_TASK);
            }
            else if(request.equals(queries.NEED_HELP)){
                doneTask(queries.NEED_HELP);
            }
            else if(request.equals(queries.DISAGREE_TASK)){
                doneTask(queries.DISAGREE_TASK);
            }
            else if(request.equals(Request.ADD_TASK_TO_USER)){
                addTask();
            }
            else if(request.equals(Request.WANT_SOME_COMMENTS)){
                sendCommentsByTask();
            }
            else if(request.equals("auth")){
                doAuth();
            }

        }
    }

    private void doneTask(String taskString){
        parser = new JsonParser();
            log.info("Клиент прислал запрос на выполненное задание, с комментарием "+ parser.parseFromJson(read).getTask().getComment().getBody());
            dbWorker.updateTask(queries.updateTask(taskString, parser.parseFromJson(read).getTask().getId()));
            dbWorker.insertComment(queries.insertComment(
                    parser.parseFromJson(read).getTask().getComment(),
                    parser.parseFromJson(read).getTask().getId(),
                    parser.parseFromJson(read).getTask().getUserId()));
        Task task = parser.parseFromJson(read).getTask();
        task.setStatus(taskString);
        read = parser.successCreateTask(task, Response.ADD_COMMENT_SUCCESS);
    }

    private void doAuth(){
        parser = new JsonParser();
        if(parser.parseFromJson(read).getRequest().equals("auth")){
            String userName = parser.parseFromJson(read).getUser().getLogin();
            String pwd = parser.parseFromJson(read).getUser().getPassword();
            String userRole = parser.parseFromJson(read).getUser().getRole();
            for(User users : dbWorker.getUserList()){
                System.out.println(users.getFIO() + " " + users.getRole());
            }
            //проверка юзера
            for(User user : dbWorker.getUserList()) {
                    if (user.getLogin().equals(userName) && user.getPassword().equals(pwd)) {
                        if (user.getRole().equals(User.adminRole)) {
                            dbWorker.queryAll();
                            read = parser.parseToAdminUsersTask(dbWorker.getUserList(), dbWorker.getTasks(), Response.ADD_ACTION_ADMIN);
                            log.info("юзер " + userName + " это админ ");
                        } else {
                            log.info("юзернейм " + userName + " - это юзер из БД");
                            dbWorker.queryById(String.valueOf(user.getId()));
                            read = parser.parseToJsonUserTasks(user, dbWorker.getTasks(), Response.ADD_TASKS_TO_USER);
                        }
                    } else if (user.getRole().equals(userRole)) {
                        read = parser.parseToJsonUserTasks(null, null, Response.GET_AWAY_GUEST);
                        log.info("юзер " + userName + " это гость ");
                    }
            }


            }
        }

        private void addTask(){
            parser = new JsonParser();
//                if (parser.parseFromJson(read).getRequest().equals(Request.ADD_TASK_TO_USER)) {
//                    log.info("добавляем задание в бд");
//                    Task task = parser.parseFromJson(read).getTask();
//                dbWorker.insertTask(queries.insertTask(task.getTitle(), task.getBody(), task.getCreated(), "0", task.getDoneTime(), task.getUser(), task.getAdress(), task.getTelephone(), task.getCommentFromUser()));
//                    read = parser.successCreateTask(task, Response.ADD_TASK_SUCCESS);
//                    log.info("добавили задание успешно");
//                }
//                else{
//                    log.info("не получилось добавить задание в бд");
//                }
        }

        private void sendCommentsByTask(){
            parser = new JsonParser();

            int id = parser.parseFromJson(read).getTask().getId();
            dbWorker.getCommentsById(id);
            read = parser.parseCommentsByTask(dbWorker.getComments(), Response.ADD_COMMENTS);
            dbWorker.removeOldComments();

        }
    }
