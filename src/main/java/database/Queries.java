package database;

import entities.Comment;
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
    public static final String CREATED = "created";
    public static final String IMPORTANCE = "importance";
    public static final String BODY = "body";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String DONE_TIME = "done_time";
    public static final String OBJECT_ID = "address_name_id_address";
    public static final String USER_ID = "users_id_users";


    public static final String USER_NAME = "login";
    public static final String USER_PWD = "password";
    public static final String USER_FIO = "FIO";
    public static final String USER_ROLE = "role";
    public static final String USER_TLF = "telephone";
    public static final String USER_EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String ORG_NAME = "org_name";

    public static final String COMMENT_BODY = "comment_body";
    public static final String USER_ID_USERS = "users_id_users";
    public static final String TASKS_ID_TASKS = "tasks_id_tasks";

    public static final String NEW_TASK = "new_task";
    public static final String DISTRIBUTED_TASK = "distributed";
    public static final String DOING_TASK = "doing";
    public static final String DONE_TASK = "done";
    public static final String DISAGREE_TASK = "disagree";
    public static final String CONTROL_TASK = "control";
    public static final String NEED_HELP = "need_help";




    public String selectAllByUserName(String id){
        return "SELECT * FROM tasks left join objects on tasks.address_name_id_address = objects.id where users_id_users='"+ id + "'";
    }

    public String selectAllUsers(){
        return "select * from users";
    }

    public String selectAllTasksObjects(){
        return "SELECT * FROM tasks"+
        " left join objects on tasks.address_name_id_address = objects.id";
    }

    public String insertTask(String title, String body, String created, String done, String doneTime, String user, String adress, String telephone, String commentUser){
        return "INSERT INTO tasks(title, body, created, done, done_time, user, adress, telephone, comment_user) VALUES('"+
                title + "', '" + body + "', '" + created + "', '" + done + "', '" + doneTime + "', '" + user + "', '" + adress + "', '" + telephone + "', '" + commentUser + "');";
    }

    public String selectCommentsByTask(int taskId){
        return "select * from user_comment where tasks_id_tasks="+taskId;
    }


    public String executeUpdate = "UPDATE tasks SET comment_user='" + commentUser + "' WHERE id = 1;";

    public String insertComment(Comment comment, int taskId, int userId){
         return "INSERT INTO `mydb`.`user_comment` (`comment_body`, `created`, `users_id_users`, `tasks_id_tasks`) VALUES ('"+ comment.getBody() +"', '"+ comment.getTs() +"', '"+ userId +"', '"+ taskId +"');";
    }

    public String updateTask(String updateTask, int id){
        return "UPDATE `mydb`.`tasks` SET `status`='"+ updateTask +"' WHERE `id`='"+ id +"';";
    }
}
