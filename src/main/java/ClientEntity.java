import database.DBWorker;
import database.Queries;
import entities.Request;
import entities.Response;
import entities.Task;
import entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientEntity extends Thread {
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
                parseRequest();

                final String data = read;
                System.out.println("---> received: " + read);
                //вызов метода отправки сообщения всем клиентам
                // - желательно в отдельном потоке
                new Thread(new Runnable() {
                    public void run() {
                        for (int i = 0; i < server.getClients().size(); i++) {
                            server.getClients().get(i).writer.println(data);
                            server.getClients().get(i).writer.flush();
                        }
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
        if(parser.parseFromJsonToUser(read).getRequest()!=null) {
            request = parser.parseFromJsonToUser(read).getRequest();
            if(request.equals("doneTask")){
                doneTask();
            }
            else if(request.equals(Request.ADD_TASKS_TO_USER)){
                addTask();
            }
            else if(request.equals("auth")){
                doAuth();
            }
        }
    }

    private void doneTask(){
        parser = new JsonParser();
        if(parser.parseFromJsonToUser(read).getRequest().equals("doneTask")) {
            System.out.println("Клиент прислал запрос на выполненное задание, с комментарием");
            System.out.println("Комментарий: "+parser.parseFromJsonToUser(read).getTask().getCommentFromUser());
            dbWorker.updateDoneTask(queries.updateDoneAddComment(parser.parseFromJsonToUser(read).getTask().getCommentFromUser(), String.valueOf(parser.parseFromJsonToUser(read).getTask().getId())));
        }
    }

    private void doAuth(){
        parser = new JsonParser();
        if(parser.parseFromJsonToUser(read).getRequest().equals("auth")){
            String userName = parser.parseFromJsonToUser(read).getUser().getName();
            String pwd = parser.parseFromJsonToUser(read).getUser().getPassword();
            String userRole = parser.parseFromJsonToUser(read).getUser().getRole();

            //проверка юзера
            for (int i = 0; i < dbWorker.getUserList().size(); i++) {
                 if (dbWorker.getUserList().get(i).getName().equals(userName) && dbWorker.getUserList().get(i).getPassword().equals(pwd)) {
                    if (dbWorker.getUserList().get(i).getRole().equals(User.adminRole)) {
                        dbWorker.queryAll();
                        read = parser.parseToAdminUsersTask(dbWorker.getUserList(), dbWorker.getTasks(), Response.ADD_ACTION_ADMIN);
                        System.out.println("юзер "+ userName + " это админ ");
                    }
                    else {
                        System.out.println("юзернейм " + userName + " - это юзер из БД");
                        dbWorker.queryByUserName(userName);
                        read = parser.parseToJsonUserTasks(dbWorker.getUserList().get(i), dbWorker.getTasks(), Response.ADD_TASKS_TO_USER);
                    }
                }
                else if(dbWorker.getUserList().get(i).getRole().equals(userRole)){
                    read = parser.parseToJsonUserTasks(null, null, Response.GET_AWAY_GUEST);
                    System.out.println("юзер "+ userName + " это гость ");
                }
            }
            }
        }

        private void addTask(){
            parser = new JsonParser();
                if (parser.parseFromJsonToUser(read).getRequest().equals(Request.ADD_TASKS_TO_USER)) {
                    System.out.println("добавляем задание в бд");
                    Task task = parser.parseFromJsonToUser(read).getTask();
                dbWorker.insertTask(queries.insertTask(task.getTitle(), task.getBody(), task.getCreated(), "0", task.getDoneTime(), task.getUser(), task.getAdress(), task.getTelephone(), task.getCommentFromUser()));
                    read = "add_task_success";
                }
        }
    }
