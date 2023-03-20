package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        String name1 = "Aboba1";
        String name2 = "Aboba2";
        String name3 = "Aboba3";
        String name4 = "Aboba4";

        userService.saveUser(name1, "lastName1", (byte) 18);
        System.out.println("User с именем – " + name1 + " добавлен в базу данных");
        userService.saveUser(name2, "lastName2", (byte) 18);
        System.out.println("User с именем – " + name2 + " добавлен в базу данных");
        userService.saveUser(name3, "lastName3", (byte) 18);
        System.out.println("User с именем – " + name3 + " добавлен в базу данных");
        userService.saveUser(name4, "lastName4", (byte) 18);
        System.out.println("User с именем – " + name4 + " добавлен в базу данных");

        userService.removeUserById(1);
        userService.getAllUsers();
        userService.dropUsersTable();

    }
}