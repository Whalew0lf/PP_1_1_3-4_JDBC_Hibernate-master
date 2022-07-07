package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Серж","Шмель",(byte) 25);
        userService.saveUser("Дэвид","Вонг",(byte) 41);
        userService.saveUser(null,"Вагин",(byte) 34);
        userService.saveUser("Фин","Шепард",(byte) 44);
        userService.saveUser("Тара","Рид",(byte) 44);
        userService.removeUserById(7L);
        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            System.out.println(user);
        }
        //userService.cleanUsersTable();
        //userService.dropUsersTable();
    }
}
