package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Util {
    final static String URL = "jdbc:mysql://localhost:3306/1_1_3db";
    final static String LOGIN = "root";
    final static String PASS = "root";
    static Driver driver;

    public static Connection getConnection() throws SQLException {
        driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection(URL, LOGIN, PASS);
        connection.setAutoCommit(false);
        return connection;
    }
}
