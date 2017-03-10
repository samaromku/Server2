package database;

import entities.Comment;
import entities.Task;
import entities.UserRole;

import java.util.ArrayList;
import java.util.List;

public class Queries {
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

    public static final String MAKE_NEW_USER = "make_new_user";
    public static final String MAKE_TASKS = "make_tasks";
    public static final String MAKE_ADDRESS = "make_address";
    public static final String WATCH_ADDRESS = "watch_address";
    public static final String CORRECTION_TASK = "correction_task";
    public static final String CORRECTION_STATUS = "correction_status";
    public static final String MAKE_EXECUTOR = "make_executor";
    public static final String CORRECTION_EXECUTOR = "correction_executor";
    public static final String WATCH_TASKS = "watch_tasks";
    public static final String COMMENT_TASKS = "comment_tasks";
    public static final String CHANGE_PASSWORD = "change_password";
    public static final String USER_ROLE_ID = "user_role_id";

    public final String NEW_TASK = "new_task";
    public final String DISTRIBUTED_TASK = "distributed";
    public final String DOING_TASK = "doing";
    public final String DONE_TASK = "done";
    public final String DISAGREE_TASK = "disagree";
    public final String CONTROL_TASK = "control";
    public final String NEED_HELP = "need_help";

    public String updateUserRoleById(UserRole userRole){
        return "UPDATE `user_role` SET `make_new_user`='"+ tfNumbers(userRole.isMakeNewUser()) +
                "', `make_tasks`='"+ tfNumbers(userRole.isMakeTasks()) + "', `make_address`='" + tfNumbers(userRole.isMakeAddress()) +
                "', `watch_address`='"+ tfNumbers(userRole.isWatchAddress()) +
                "', `correction_task`='"+ tfNumbers(userRole.isCorrectionTask()) +"', `correction_status`='"+ tfNumbers(userRole.isCorrectionStatus()) +
                "', `make_executor`='"+ tfNumbers(userRole.isMakeExecutor()) +
                "', `correction_executor`='" + tfNumbers(userRole.isCorrectionExecutor()) + "', `watch_tasks`='"+ tfNumbers(userRole.isWatchTasks()) +
                "', `comment_tasks`='"+ tfNumbers(userRole.isCommentTasks()) +"', " +
                "`change_password`='"+ tfNumbers(userRole.isChangePassword()) +"' WHERE `id_user_role`='"+ userRole.getId() +"';";
    }

    public String selectAllByUserName(String id){
        return "SELECT * FROM tasks left join objects on tasks.address_name_id_address = objects.id where users_id_users='"+ id + "'";
    }

    public String selectAllUsers(){
        return "SELECT * FROM users left join user_role on users.user_role_id=user_role.id_user_role";
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

    public String insertComment(Comment comment, int taskId, int userId){
         return "INSERT INTO `mydb`.`user_comment` (`comment_body`, `created`, `users_id_users`, `tasks_id_tasks`) VALUES ('"+ comment.getBody() +"', '"+ comment.getTs() +"', '"+ userId +"', '"+ taskId +"');";
    }

    public String updateTask(String updateTask, int id){
        return "UPDATE `mydb`.`tasks` SET `status`='"+ updateTask +"' WHERE `id`='"+ id +"';";
    }

    private int tfNumbers(boolean b){
        if(b)
            return 1;
        else return 0;
    }
}
