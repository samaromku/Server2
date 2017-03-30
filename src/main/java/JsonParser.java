import com.google.gson.Gson;
import entities.*;
import network.Request;
import network.Response;

import java.util.List;

public class JsonParser {
    Gson gson = new Gson();

    public Request parseFromJson(String json) {
        return gson.fromJson(json, Request.class);
    }

    public String successAddCoords(){
        return new Gson().toJson(new Response(Response.SUCCESS_ADD_COORDS));
    }

    public String successUpdateTask(){
        return new Gson().toJson(new Response(Response.SUCCESS_UPDATE_TASK));
    }

    public String parseAddressesToUser(List<Address> addresses){
        return new Gson().toJson(new Response(Response.ADD_ADDRESSES_TO_USER, addresses));
    }

    public String parseLastUserCoords(List<UserCoords> userCoordsList){
        return new Gson().toJson(Response.getUserCoords(userCoordsList, Response.ADD_LATEST_USER_COORDS));
    }

    public String parseSuccessUpdateUserRole(){
        return new Gson().toJson(new Response(Response.UPDATE_USER_ROLE_SUCCESS));
    }

    public String parseSuccessInsertUserRole(){
        return new Gson().toJson(new Response(Response.INSERT_USER_ROLE_SUCCESS));
    }

    public String notSuccess(){
        return new Gson().toJson(new Response(Response.NOT_SUCCESS));
    }



    public String parseToJsonUserTasks(List<User>users, User user, List<Task> taskList, String response){
        return new Gson().toJson(new Response(users, user, taskList, response));
    }

    public String parseToJsonGuest(){
        return new Gson().toJson(new Response(Response.GET_AWAY_GUEST));
    }

    public String parseToAdminUsersTask(List<User> users, List<Task> taskList, String response){
        return new Gson().toJson(new Response(users, taskList, response));
    }

    public String successAddComment(){
        return new Gson().toJson(new Response(Response.ADD_COMMENT_SUCCESS));
    }

    public String successCreateTask(){
        return new Gson().toJson(new Response(Response.ADD_TASK_SUCCESS));
    }



    public String successAddUser(User user, String response){
        return new Gson().toJson(new Response(user, response));
    }

    public String parseCommentsByTask(List<Comment> comments, String response){
        return new Gson().toJson(new Response(comments, response));
    }
}
