import com.google.gson.Gson;
import entities.Request;
import entities.Response;
import entities.Task;
import entities.User;

import java.util.List;

public class JsonParser {
    Gson gson = new Gson();

    public Request parseFromJsonToUser(String json) {
        return gson.fromJson(json, Request.class);
    }

    public String parseToJsonUserTasks(User user, List<Task> taskList, String response){
        return new Gson().toJson(new Response(user, taskList, response));
    }

    public String parseToAdminUsersTask(List<User> users, List<Task> taskList, String response){
        return new Gson().toJson(new Response(users, taskList, response));
    }
}
