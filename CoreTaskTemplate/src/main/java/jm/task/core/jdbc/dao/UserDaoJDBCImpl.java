package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS `myfirstdb`.`users` (\n" +
                "  `id` INTEGER NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastName` VARCHAR(45) NOT NULL,\n" +
                "  `age` INT NOT NULL,\n" +
                "    PRIMARY KEY (`id`))\n" +
                "    ENGINE = InnoDB\n" +
                "    DEFAULT CHARACTER SET = utf8;";

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCreateTable);
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }


    @Override
    public void dropUsersTable() {
        String sqlDropTable = "DROP TABLE IF EXISTS`myfirstdb`.`users`;";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sqlDropTable);
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sqlSaveUser = "INSERT INTO `myfirstdb`.`users` (`name`, `lastName`, `age`) VALUES (?, ?, ?);";

        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlSaveUser)) {

            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);

            ps.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        String sqlRemoveUserById = "DELETE FROM `myfirstdb`.`users` WHERE (`id` = ?);";

        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlRemoveUserById)) {

            ps.setInt(1, (int) id);

            ps.executeUpdate();
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sqlGetAllUsers = "SELECT * FROM myfirstdb.users;";
        List<User> userList = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             Statement st = connection.createStatement()) {

            ResultSet resultSet = st.executeQuery(sqlGetAllUsers);
            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("LastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException th) {
            th.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String sqlCleanUsersTable = "TRUNCATE `myfirstdb`.`users`;";

        try (Connection connection = Util.getConnection();
             Statement st = connection.createStatement()) {

            st.executeUpdate(sqlCleanUsersTable);
        } catch (SQLException th) {
            th.printStackTrace();
        }
    }
}
