package database;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.apache.log4j.Logger;

import java.sql.*;

public class DBStart {
    private Logger log = Logger.getLogger(DBStart.class.getName());
    private final String URL = "jdbc:mysql://localhost:3306/mydb?useSSL=false";
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
                log.info("соединение c бд установлено");
            }
            if(connection.isClosed()){
                log.info("соединение с бд закрыто");
            }
        } catch (SQLException e) {
            log.error("не удалось загрузить драйвер");
        }
    }
}
