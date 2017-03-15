package database;

import entities.*;
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
                        resultSet.getInt(Queries.ADDRESS_ID),
                        resultSet.getString(Queries.ADDRESS),
                        resultSet.getString(Queries.ORG_NAME)));
            }
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public List<User> getUsersForSimpleUser(){
        List<User> userList1 = new ArrayList<>();
        try {
            statement = dbStart.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(queries.selectUsersForSimpleUser());
            while (resultSet.next()){
                User user = new User(
                        resultSet.getInt(Queries.ID),
                        resultSet.getString(Queries.USER_NAME),
                        resultSet.getString(Queries.USER_FIO),
                        resultSet.getString(Queries.USER_TLF),
                        resultSet.getString(Queries.USER_EMAIL));
                userList1.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList1;
    }

    public List<Address> getAllAddresses(){
        List<Address> addresses = new ArrayList<>();
        try {
            statement = dbStart.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(queries.getAddresses());
            while (resultSet.next()){
                addresses.add(new Address(
                        resultSet.getInt(Queries.ID),
                        resultSet.getString(Queries.ORG_NAME),
                        resultSet.getString(Queries.ADDRESS)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addresses;
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
                        resultSet.getInt(Queries.ADDRESS_ID),
                        resultSet.getString(Queries.ADDRESS),
                        resultSet.getString(Queries.ORG_NAME)));
            }
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public void updateUserRole(UserRole userRole){
        try {

            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.updateUserRoleById(userRole));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUserRole(UserRole userRole){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.insertUserRole(userRole));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertTask(Task task){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.insertTask(task));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
                , new UserRole(
                    resultSet.getInt(Queries.USER_ROLE_ID),
                        resultSet.getBoolean(Queries.MAKE_NEW_USER),
                        resultSet.getBoolean(Queries.MAKE_TASKS),
                        resultSet.getBoolean(Queries.CORRECTION_TASK),
                        resultSet.getBoolean(Queries.MAKE_ADDRESS),
                        resultSet.getBoolean(Queries.WATCH_ADDRESS),
                        resultSet.getBoolean(Queries.CORRECTION_STATUS),
                        resultSet.getBoolean(Queries.MAKE_EXECUTOR),
                        resultSet.getBoolean(Queries.CORRECTION_EXECUTOR),
                        resultSet.getBoolean(Queries.WATCH_TASKS),
                        resultSet.getBoolean(Queries.COMMENT_TASKS),
                        resultSet.getBoolean(Queries.CHANGE_PASSWORD),
                    resultSet.getInt(Queries.ID))
                ));
            }
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public void addNewUser(User user){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.addNewUser(user));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getCommentsById(int taskId){
        try {
            statement = dbStart.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(queries.selectCommentsByTask(taskId));
            while (resultSet.next()){
                Comment comment = new Comment(
                        resultSet.getInt(Queries.ID),
                        resultSet.getString(Queries.CREATED),
                        resultSet.getString(Queries.COMMENT_BODY),
                        resultSet.getInt(Queries.USER_ID_USERS),
                        resultSet.getInt(Queries.TASKS_ID_TASKS)
                );
                comments.add(comment);
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
