package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;
    final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
        } catch (SQLException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                CREATE TABLE `users` (
                `id` INT NOT NULL AUTO_INCREMENT,
                `name` VARCHAR(45) NOT NULL,
                `lastName` VARCHAR(45) NOT NULL,
                `age` TINYINT NOT NULL,
                PRIMARY KEY (`id`));""");
            connection.commit();
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE users");
            connection.commit();
        } catch (SQLException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES (default, ?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
            System.out.println("user saved");
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while(resultSet.next()) {
                userList.add(new User(resultSet.getString(2),
                        resultSet.getString(3),resultSet.getByte(4)));
            }
            connection.commit();
            return userList;
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
            return userList;
        }
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE users");
            connection.commit();
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        }
    }
}
