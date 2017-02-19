import com.google.gson.Gson;
import entities.*;

import java.util.List;

public class JsonParser {
    Gson gson = new Gson();

    public Request parseFromJson(String json) {
        return gson.fromJson(json, Request.class);
    }

    public String parseToJsonUserTasks(User user, List<Task> taskList, String response){
        return new Gson().toJson(new Response(user, taskList, response));
    }

    public String parseToAdminUsersTask(List<User> users, List<Task> taskList, String response){
        return new Gson().toJson(new Response(users, taskList, response));
    }

    public String successCreateTask(Task task, String response){
        return new Gson().toJson(new Response(task, response));
    }

    public String parseCommentsByTask(List<Comment> comments, String response){
        return new Gson().toJson(new Response(comments, response));
    }
}
