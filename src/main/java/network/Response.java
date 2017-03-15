package network;

import entities.*;

import java.util.List;

public class Response {
    private String response;
    private User user;
    private Task task;
    private List<Task> taskList;
    private List<User> userList;
    private List<Comment> comments;
    private List<Address> addresses;
    private UserRole userRole;
    public static final String ADD_TASKS_TO_USER = "addTasksToUser";
    public static final String GET_AWAY_GUEST = "getAwayGuest";
    public static final String ADD_ACTION_ADMIN = "addActionAdmin";
    public static final String ADD_TASK_SUCCESS = "add_task_success";
    public static final String UPDATE_USER_ROLE_SUCCESS = "update_user_role_success";
    public static final String INSERT_USER_ROLE_SUCCESS = "insert_user_role_success";
    public static final String ADD_COMMENTS = "add_comments";
    public static final String ADD_COMMENT_SUCCESS = "add_comment_success";
    public static final String ADD_ADDRESSES_TO_USER = "add_addresses_to_user";
    public static final String SUCCESS_ADD_USER = "success_add_user";


    public Response(List<User>users, User user, List<Task>taskList, String response) {
        this.userList = users;
        this.user = user;
        this.taskList = taskList;
        this.response = response;
    }

    public Response(Task task, String response){
        this.task = task;
        this.response = response;
    }

    public Response(String response, List<Address> addresses){
        this.response = response;
        this.addresses = addresses;
    }

    public Response(String response){
        this.response = response;
    }


    public Response(List<Comment> comments, String response){
        this.comments = comments;
        this.response = response;
    }

    public Response(List<User> users, List<Task>taskList, String response) {
        this.userList = users;
        this.taskList = taskList;
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public User getUser() {
        return user;
    }

    public Task getTask() {
        return task;
    }
}
