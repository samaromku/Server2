package managers;


import entities.UserCoords;

import java.util.ArrayList;
import java.util.List;

public class UserCoordsManager {
    private UserCoords userCoords;
    private List<UserCoords>userCoordsList;

    public UserCoordsManager(){
        userCoordsList = new ArrayList<>();
    }

    public List<UserCoords> getUserCoordsList() {
        return userCoordsList;
    }

    public UserCoords getUserCoords() {
        return userCoords;
    }

    public void setUserCoords(UserCoords userCoords) {
        this.userCoords = userCoords;
    }

    public void addUserCoords(UserCoords userCoords){
        userCoordsList.add(userCoords);
    }


}
