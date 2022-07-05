package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    final static String URL = "jdbc:mysql://localhost:3306/1_1_3db";
    final static String LOGIN = "root";
    final static String PASS = "root";
    final static String DIALECT = "org.hibernate.dialect.MySQL8Dialect";
    final static String DRIVERNAME = "com.mysql.cj.jdbc.Driver";
    static Driver driver;
    private static Connection connection;
    private static SessionFactory sessionFactory;
    private static Session hibernateSession;

    public static Connection getConnection() throws SQLException {
        driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        connection = DriverManager.getConnection(URL, LOGIN, PASS);
        connection.setAutoCommit(false);
        return connection;
    }

    public static Session getSession() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.url", URL);
        properties.setProperty("dialect", DIALECT);
        properties.setProperty("hibernate.connection.username", LOGIN);
        properties.setProperty("hibernate.connection.password", PASS);
        properties.setProperty("hibernate.connection.driver_class", DRIVERNAME);
        sessionFactory = new Configuration().addProperties(properties).addAnnotatedClass(User.class).buildSessionFactory();
        hibernateSession = sessionFactory.openSession();
        return hibernateSession;
    }
}
