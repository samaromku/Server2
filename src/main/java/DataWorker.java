import database.DBWorker;
import entities.Comment;
import entities.Task;
import entities.User;
import entities.UserRole;
import managers.*;

import java.util.ArrayList;
import java.util.List;

public class DataWorker {
    DBWorker dbWorker = new DBWorker();
    private UsersManager usersManager = new UsersManager();
    private TasksManager tasksManager = new TasksManager();
    private UserRolesManager userRolesManager = new UserRolesManager();
    private CommentsManager commentsManager = new CommentsManager();
    private AddressManager addressManager = new AddressManager();
    private UserCoordsManager userCoordsManager = new UserCoordsManager();

    private User createNewUser;

    public User getCreateNewUser() {
        return createNewUser;
    }

    public void setCreateNewUser(User createNewUser) {
        this.createNewUser = createNewUser;
    }

    public DataWorker(){}

    public void addLists(){
        usersManager.addAll(dbWorker.getUserList());
        tasksManager.addAll(dbWorker.getTasks());
        for (int i = 0; i < usersManager.getUsers().size(); i++) {
            userRolesManager.add(usersManager.getUsers().get(i).getUserRole());
        }
        commentsManager.addAll(dbWorker.getComments());
        addressManager.addAll(dbWorker.getAllAddresses());
    }


}
