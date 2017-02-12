package database;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;

public class DBStart {
    private final String URL = "jdbc:mysql://localhost:3306/sav_test?useSSL=false";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }


    public DBStart(){
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if(!connection.isClosed()){
                System.out.println("соединение c бд установлено");
            }
            if(connection.isClosed()){
                System.out.println("соединение с бд закрыто");
            }
        } catch (SQLException e) {
            System.out.println("не удалось загрузить драйвер");
        }
    }
}
