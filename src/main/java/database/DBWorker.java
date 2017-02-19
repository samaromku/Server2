package database;

import entities.Comment;
import entities.Task;
import entities.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBWorker{
    private DBStart dbStart = new DBStart();
    private List<Task> tasks = new ArrayList<Task>();
    private List<User> userList = new ArrayList<User>();
    private List<Comment> comments = new ArrayList<>();
    private Statement statement;
    private Queries queries = new Queries();
    Logger log = Logger.getLogger(DBWorker.class.getName());

    public List<Comment> getComments() {
        return comments;
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void queryById(String id){
        try {
            statement = dbStart.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(queries.selectAllByUserName(id));
            while (resultSet.next()) {
                tasks.add(new Task(
                        resultSet.getInt(Queries.ID),
                        resultSet.getString(Queries.CREATED),
                        resultSet.getString(Queries.IMPORTANCE),
                        resultSet.getString(Queries.BODY),
                        resultSet.getString(Queries.STATUS),
                        resultSet.getString(Queries.TYPE),
                        resultSet.getString(Queries.DONE_TIME),
                        resultSet.getInt(Queries.USER_ID),
                        resultSet.getString(Queries.ADDRESS),
                        resultSet.getString(Queries.ORG_NAME)));
            }
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public void queryAll(){
        try {
            statement = dbStart.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(queries.selectAllTasksObjects());
            while (resultSet.next()) {
                tasks.add(new Task(
                        resultSet.getInt(Queries.ID),
                        resultSet.getString(Queries.CREATED),
                        resultSet.getString(Queries.IMPORTANCE),
                        resultSet.getString(Queries.BODY),
                        resultSet.getString(Queries.STATUS),
                        resultSet.getString(Queries.TYPE),
                        resultSet.getString(Queries.DONE_TIME),
                        resultSet.getInt(Queries.USER_ID),
                        resultSet.getString(Queries.ADDRESS),
                        resultSet.getString(Queries.ORG_NAME)));
            }
        } catch (SQLException e) {
            log.error(e);
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
                resultSet.getString(Queries.USER_FIO),
                resultSet.getString(Queries.USER_ROLE),
                resultSet.getString(Queries.USER_TLF),
                resultSet.getString(Queries.USER_EMAIL)
                ));
            }
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public void getCommentsById(int taskId){
        try {
            statement = dbStart.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(queries.selectCommentsByTask(taskId));
            while (resultSet.next()){
                comments.add(new Comment(
                        resultSet.getInt(Queries.ID),
                        resultSet.getString(Queries.CREATED),
                        resultSet.getString(Queries.COMMENT_BODY),
                        resultSet.getInt(Queries.USER_ID_USERS),
                        resultSet.getInt(Queries.TASKS_ID_TASKS)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(String insertQuery){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertComment(String updateQuery){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(updateQuery);
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public void removeOldComments(){
        if(comments.size()>0){
            comments.clear();
        }
    }
}
