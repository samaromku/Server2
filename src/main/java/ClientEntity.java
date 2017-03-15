import database.DBWorker;
import database.Queries;
import network.Request;
import network.Response;
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
    private String write;
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
                final String data = write;
                log.info("Сообщение клиенту: " + write);
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
            switch (request){
                case Queries.DONE_TASK:
                    doneTask(Queries.DONE_TASK);
                    return;

                case Queries.NEED_HELP:
                    doneTask(Queries.NEED_HELP);
                    return;
                case Queries.DISAGREE_TASK:
                    doneTask(Queries.DISAGREE_TASK);
                    return;

                case Request.ADD_TASK_TO_USER:
                    addTask();
                    return;
                case Request.WANT_SOME_COMMENTS:
                    sendCommentsByTask();
                    return;
                case Request.CHANGE_PERMISSION_PLEASE:
                    updateUserRole();
                    return;

                case Request.GIVE_ME_ADDRESSES_PLEASE:
                    giveAddresses();
                    return;

                case Request.ADD_NEW_ROLE:
                    addNewRole();
                    return;

                case Request.ADD_NEW_USER:
                    addNewUser();
                    return;

                case Request.AUTH:
                    doAuth();

                    default:
            }
        }
    }

    private void giveAddresses(){
        parser = new JsonParser();
        log.info("Подготавливаем адреса для клиента");
        dbWorker.getAllAddresses();
        write = parser.parseAddressesToUser(dbWorker.getAllAddresses());
    }

    private void doneTask(String taskString){
        parser = new JsonParser();
            log.info("Клиент прислал запрос на выполненное задание, с комментарием "+ parser.parseFromJson(read).getComment().getBody());
            dbWorker.updateTask(queries.updateTask(taskString, parser.parseFromJson(read).getComment().getTaskId()));
            dbWorker.insertComment(queries.insertComment(parser.parseFromJson(read).getComment()));
        write = parser.successAddComment();
    }

    private void addNewUser(){
        parser = new JsonParser();
        dbWorker.addNewUser(parser.parseFromJson(read).getUser());
        write = parser.successAddUser();
    }

    private void doAuth(){
        parser = new JsonParser();
        if(parser.parseFromJson(read).getRequest().equals("auth")){
            String userName = parser.parseFromJson(read).getUser().getLogin();
            String pwd = parser.parseFromJson(read).getUser().getPassword();
            String userRole = parser.parseFromJson(read).getUser().getRole();
            //проверка юзера
            for(User user : dbWorker.getUserList()) {
                    if (user.getLogin().equals(userName) && user.getPassword().equals(pwd)) {
                        if (user.getRole().equals(User.ADMIN_ROLE)) {
                            dbWorker.queryAll();
                            write = parser.parseToAdminUsersTask(dbWorker.getUserList(), dbWorker.getTasks(), Response.ADD_ACTION_ADMIN);
                            log.info("юзер " + userName + " это админ ");
                        } else {
                            dbWorker.queryById(String.valueOf(user.getId()));
                            write = parser.parseToJsonUserTasks(dbWorker.getUsersForSimpleUser(), user, dbWorker.getTasks(), Response.ADD_TASKS_TO_USER);
                            log.info("юзернейм " + userName + " - это юзер из БД");
                        }
                    } else if (user.getRole().equals(userRole)) {
                        write = parser.parseToJsonUserTasks();
                        log.info("юзер " + userName + " это гость ");
                    }
            }


            }
        }

        private void addTask(){
            parser = new JsonParser();
                    Task task = parser.parseFromJson(read).getTask();
                    dbWorker.insertTask(task);
                    write = parser.successCreateTask();
        }

        private void updateUserRole(){
            parser = new JsonParser();
            dbWorker.updateUserRole(parser.parseFromJson(read).getUserRole());
            write = parser.parseSuccessUpdateUserRole();
        }

        private void sendCommentsByTask(){
            parser = new JsonParser();
            int id = parser.parseFromJson(read).getTask().getId();
            dbWorker.getCommentsById(id);
            write = parser.parseCommentsByTask(dbWorker.getComments(), Response.ADD_COMMENTS);
            dbWorker.removeOldComments();
        }

        private void addNewRole(){
            parser = new JsonParser();
            dbWorker.insertUserRole(parser.parseFromJson(read).getUserRole());
            write = parser.parseSuccessInsertUserRole();
        }
    }
