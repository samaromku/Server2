package entities;

import entities.Task;
import entities.User;

public class Request {
    private String request;
    private User user;
    private Task task;
    public static final String ADD_TASKS_TO_USER = "add_task_to_server";

    public Request(Task task, String request){
        this.task = task;
        this.request = request;
    }

    public Request(User user, String request){
        this.user = user;
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public User getUser() {
        return user;
    }

    public Task getTask() {
        return task;
    }
}