package entities;

import entities.Task;
import entities.User;

import java.util.List;

public class Response {
    private String response;
    private User user;
    private Task task;
    private List<Task> taskList;
    private List<User> userList;
    public static final String ADD_TASKS_TO_USER = "addTasksToUser";
    public static final String GET_AWAY_GUEST = "getAwayGuest";
    public static final String ADD_ACTION_ADMIN = "addActionAdmin";

    public Response(User user, List<Task>taskList, String response) {
        this.user = user;
        this.taskList = taskList;
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
