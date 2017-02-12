package database;

import entities.Task;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBWorker{
    private DBStart dbStart = new DBStart();
    private List<Task> tasks = new ArrayList<Task>();
    private List<User> userList = new ArrayList<User>();
    private Statement statement;
    private Queries queries = new Queries();

    public List<User> getUserList() {
        return userList;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void queryByUserName(String userName){
        try {
            statement = dbStart.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(queries.selectAllByUserName(userName));
            while (resultSet.next()) {
                tasks.add(new Task(resultSet.getInt(Queries.ID),
                        resultSet.getString(Queries.TITLE),
                        resultSet.getString(Queries.BODY),
                        resultSet.getString(Queries.CREATED),
                        resultSet.getBoolean(Queries.DONE),
                        resultSet.getString(Queries.DONE_TIME),
                        resultSet.getString(Queries.USER),
                        resultSet.getString(Queries.ADRESS),
                        resultSet.getString(Queries.TELEPHONE),
                        resultSet.getString(Queries.COMMENT_USER)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void queryAll(){
        try {
            statement = dbStart.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(queries.selectAllTasks());
            while (resultSet.next()) {
                tasks.add(new Task(resultSet.getInt(Queries.ID),
                        resultSet.getString(Queries.TITLE),
                        resultSet.getString(Queries.BODY),
                        resultSet.getString(Queries.CREATED),
                        resultSet.getBoolean(Queries.DONE),
                        resultSet.getString(Queries.DONE_TIME),
                        resultSet.getString(Queries.USER),
                        resultSet.getString(Queries.ADRESS),
                        resultSet.getString(Queries.TELEPHONE),
                        resultSet.getString(Queries.COMMENT_USER)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void queryById(){
        try {
            statement = dbStart.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(queries.selectAllUsers());
            while (resultSet.next()){
                userList.add(new User(
                resultSet.getInt(Queries.ID),
                resultSet.getString(Queries.USER_NAME),
                resultSet.getString(Queries.USER_PWD),
                resultSet.getString(Queries.USER_ROLE)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTask(String insertQuery){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDoneTask(String updateQuery){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(updateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
