package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public static final Connection connection;

    static {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {

        Connection connection1 = null;
        try (Connection connection = Util.getConnection();Statement statement = connection.createStatement();) {

            connection.setAutoCommit(false);
            statement.execute("create table if not exists users " +
                    "(   id       bigint(20) not null auto_increment ," +
                    "    name     varchar(40) null,"   +
                    "    lastname varchar(40) null,"   +
                    "    age      tinyint     null,"   +
                    "    primary key(id)           "   +
                    ") ;");

            connection.commit();
            connection1 = connection;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection1.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void dropUsersTable() {
        Connection connection1 = null;
        try (Connection connection = Util.getConnection();Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            statement.execute("drop table if exists users");
            connection.commit();
            connection1 = connection;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection1.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection1 = null;
        try (Connection connection = Util.getConnection();PreparedStatement prstatement = connection.prepareStatement(
                "insert into users (name, lastname, age) values( ?, ?, ?)");) {
            connection.setAutoCommit(false);
            prstatement.setString(1,name);
            prstatement.setString(2,lastName);
            prstatement.setByte(3, age);
            prstatement.executeUpdate();
            connection.commit();

            System.out.println("User с именем – " + name + " добавлен в базу данных");
            connection1 = connection;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection1.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection1 = null;
        try (Connection connection = Util.getConnection();PreparedStatement statement = connection.prepareStatement("delete from users where id=?")) {
            connection.setAutoCommit(false);
            statement.setLong(1,id);
            statement.executeUpdate();
            connection.commit();
            connection1 = connection;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection1.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection connection1 = null;
        try (Connection connection = Util.getConnection();Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            connection.commit();
            connection1 = connection;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection1.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return users;
    }

    public void cleanUsersTable() {

        Connection connection1 = null;
        try (Connection connection = Util.getConnection();Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            statement.executeUpdate("delete from users");
            connection.commit();
            connection1 = connection;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection1.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}