package database;

import entities.Task;

import java.util.ArrayList;
import java.util.List;

public class Queries {
    private static String title = "Закончить начатое";
    private static String body = "Сделать козни";
    private static String created = "23.02";
    private static String done = "0";
    private static String doneTime = "14.05";
    private static String user = "Антон";
    private static String adress = "Лесной 39";
    private static String telephone = "098874343";
    private static String commentUser = "Все сделано";
    private static List<Task> tasks = new ArrayList<Task>();

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String CREATED = "created";
    public static final String DONE = "done";
    public static final String DONE_TIME = "done_time";
    public static final String USER = "user";
    public static final String ADRESS = "adress";
    public static final String TELEPHONE = "telephone";
    public static final String COMMENT_USER = "comment_user";

    public static final String USER_NAME = "user_name";
    public static final String USER_PWD = "user_pwd";
    public static final String USER_ROLE = "user_role";

    public String selectAllByUserName(String userName){
        return "select * from tasks where user = '" + userName + "';";
    }

    public String selectAllUsers(){
        return "select * from users";
    }

    public String selectAllTasks(){
        return "select * from tasks";
    }



    public String insertTask(String title, String body, String created, String done, String doneTime, String user, String adress, String telephone, String commentUser){
        return "INSERT INTO tasks(title, body, created, done, done_time, user, adress, telephone, comment_user) VALUES('"+
                title + "', '" + body + "', '" + created + "', '" + done + "', '" + doneTime + "', '" + user + "', '" + adress + "', '" + telephone + "', '" + commentUser + "');";
    }

    public String executeUpdate = "UPDATE tasks SET comment_user='" + commentUser + "' WHERE id = 1;";

    public String updateDoneAddComment(String commentUser, String id){
        return "UPDATE tasks SET done=1, comment_user='" + commentUser + "' WHERE id='" + id + "';";
    }
}
