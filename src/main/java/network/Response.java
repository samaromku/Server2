package network;

import entities.*;
import entities.Object;

import java.util.List;

public class Response {
    private String response;
    private User user;
    private Task task;
    private List<Task> taskList;
    private List<User> userList;
    private List<Comment> comments;
    private List<Address> addresses;
    private List<UserCoords>userCoordsList;
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
    public static final String ADD_LATEST_USER_COORDS = "add_latest_user_coords";
    public static final String SUCCESS_ADD_USER = "success_add_user";
    public static final String SUCCESS_ADD_COORDS = "success_add_coords";
    public static final String SUCCESS_UPDATE_TASK = "success_update_task";
    public static final String NOT_SUCCESS = "not_success";
    public static final String SUCCESS_REMOVE_USER = "success_remove_user";
    public static final String SUCCESS_REMOVE_TASK = "success_remove_task";


    public Response() {
    }

    public Response(List<User>users, User user, List<Task>taskList, String response) {
        this.userList = users;
        this.user = user;
        this.taskList = taskList;
        this.response = response;
    }

    public static Response getUserCoords(List<UserCoords>userCoords, String response){
        Response response1 = new Response();
        response1.userCoordsList = userCoords;
        response1.response = response;
        return response1;
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

    public Response(User user, String response){
        this.user = user;
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
