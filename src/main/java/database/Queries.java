package database;

import entities.*;

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
    public static final String ADDRESS_ID = "address_name_id_address";


    public static final String USER_NAME = "login";
    public static final String USER_PWD = "password";
    public static final String USER_FIO = "FIO";
    public static final String USER_ROLE = "role";
    public static final String USER_TLF = "telephone";
    public static final String USER_EMAIL = "email";

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
    public static final String ID_USER_ROLE = "id_user_role";

    public static final String ORG_NAME = "org_name";
    public static final String ADDRESS = "address";

    public static final String COORDS_LAT = "coords_lat";
    public static final String COORDS_LON = "coords_lon";
    public static final String TS = "ts";

//    public static final String NEW_TASK = "new_task";
//    public static final String DISTRIBUTED_TASK = "distributed";
//    public static final String DOING_TASK = "doing";
//    public static final String DONE_TASK = "done";
//    public static final String DISAGREE_TASK = "disagree";
//    public static final String CONTROL_TASK = "control";
//    public static final String NEED_HELP = "need_help";


    public static final String NEW_TASK = "новое задание";
    public static final String DISTRIBUTED_TASK = "распределено";
    public static final String DOING_TASK = "выполняется";
    public static final String DONE_TASK = "выполнено";
    public static final String DISAGREE_TASK = "отказ";
    public static final String CONTROL_TASK = "контроль";
    public static final String NEED_HELP = "нужна помощь";

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

    public String addUserCoords(UserCoords userCoords){
        return "INSERT INTO `mydb`.`user_coords` (`coords_lat`, `coords_lon`, `users_id_users`, `ts`) VALUES ('" +
                userCoords.getLat()+"', '"+userCoords.getLog()+"', '"+userCoords.getUserId()+"', '"+userCoords.getTs()+"');";
    }

    public String getLastUserCoords(){
        return "SELECT id, coords_lat, coords_lon, users_id_users, address, max(ts) as ts from user_coords group by users_id_users;";
    }

    public String removeCommentsByTaskId(Task task){
        return "DELETE FROM `user_comment` WHERE `tasks_id_tasks`='"+task.getId()+"';";
    }

    public String removeTask(Task task){
        return "DELETE FROM `tasks` WHERE `id`='"+task.getId()+"';";
    }

    public String removeCommentsByUserId(User user){
        return "DELETE FROM `user_comment` WHERE `users_id_users`='"+user.getId()+"';";
    }

    public String removeUserRoleByUserId(User user){
        return "DELETE FROM `user_role` WHERE `users_id_users`='"+user.getId()+"';";
    }

    public String removeCoordesByUserId(User user){
        return "DELETE FROM `user_coords` WHERE `users_id_users`='"+user.getId()+"';";
    }

    public String removeUser(User user){
        return "DELETE FROM `users` WHERE `id`='"+user.getId()+"';";
    }

    public String addNewUser(User user){
        return "INSERT INTO `users` (`id`, `login`, `password`, `FIO`, `role`, `telephone`, `email`) VALUES " +
                "('"+ user.getId() +"', '" + user.getLogin() +"', '"+ user.getPassword() +
                "', '"+ user.getFIO() +"', '"+ user.getRole() +"', '"+ user.getTelephone() +
                "', '" + user.getEmail() + "');";
    }

    public String selectAllByUserId(String id){
        return "SELECT * FROM tasks left join objects on tasks.address_name_id_address = objects.id where not status='"+ DONE_TASK +
                "' and users_id_users='"+id+"' order by "+IMPORTANCE+" , done_time";
    }

    public String selectUsersForSimpleUser(){
        return "SELECT id, login, FIO, telephone, email FROM users;";
    }

    public String selectAllUsers(){
        return "SELECT * FROM users left join user_role on users.id=user_role.users_id_users";
    }

    public String selectAllTasksObjects(){
        return "SELECT * FROM tasks left join objects on tasks.address_name_id_address = objects.id order by status, done_time";
    }

    public String insertTask(Task task){
        return "INSERT INTO `mydb`.`tasks` (`id`, `created`, `importance`, `status`, `body`, `done_time`, " +
                "`address_name_id_address`, `users_id_users`, `type`) VALUES ('" +
                task.getId()+"', '"+task.getCreated()+"', '"+task.getImportance()+"', '"+task.getStatus()+
                "', '"+task.getBody()+"', '"+task.getDoneTime()+"', '"+task.getAddressId()+"', '"+task.getUserId()+
                "', '"+task.getType()+"');";
    }

    public String updateTask(Task task){
        return "UPDATE `tasks` SET `created`='"+task.getCreated()+"', `importance`='"+task.getImportance()
                +"', `status`='"+task.getStatus()+"', `body`='"+task.getBody()
                +"', `done_time`='"+task.getDoneTime()+"', `address_name_id_address`='"+task.getAddressId()
                +"', `users_id_users`='"+task.getUserId()+"', `type`='"+task.getType()+"' WHERE `id`='"+task.getId()+"';";
    }

    public String selectCommentsByTask(int taskId){
        return "select * from user_comment where tasks_id_tasks="+taskId + " order by created desc";
    }

    public String insertComment(Comment comment){
         return "INSERT INTO `user_comment` (`comment_body`, `created`, `users_id_users`, `tasks_id_tasks`) VALUES ('"+
                 comment.getBody() +"', '"+ comment.getTs() +"', '"+ comment.getUserId() +"', '"+ comment.getTaskId() +"');";
    }

    public String updateTask(String updateTask, int id){
        return "UPDATE `tasks` SET `status`='"+ updateTask +"' WHERE `id`='"+ id +"';";
    }

    public String getAddresses(){
        return "select * from objects";
    }

    public String insertUserRole(UserRole userRole){
        return "INSERT INTO `mydb`.`user_role` (`id_user_role`, `make_new_user`, `make_tasks`, `make_address`, `watch_address`, `correction_task`, `correction_status`, " +
                "`make_executor`, `correction_executor`, `watch_tasks`, `comment_tasks`, `change_password`, `users_id_users`) VALUES ('" + userRole.getId()+"', '"+
                tfNumbers(userRole.isMakeNewUser())  +"', '"+ tfNumbers(userRole.isMakeTasks()) + "', '" + tfNumbers(userRole.isMakeAddress()) +
            "', '"+ tfNumbers(userRole.isWatchAddress()) +
                "', '"+tfNumbers(userRole.isCorrectionTask())+"', '"+tfNumbers(userRole.isCorrectionStatus())+"', '" +
                tfNumbers(userRole.isMakeExecutor())+"', '"+tfNumbers(userRole.isCorrectionExecutor())+"', '"+tfNumbers(userRole.isWatchTasks())+"', '"+tfNumbers(userRole.isCommentTasks())+"', '" +
                tfNumbers(userRole.isChangePassword())+"', '"+userRole.getUserId()+"');";

    }

    private int tfNumbers(boolean b){
        if(b)
            return 1;
        else return 0;
    }
}
