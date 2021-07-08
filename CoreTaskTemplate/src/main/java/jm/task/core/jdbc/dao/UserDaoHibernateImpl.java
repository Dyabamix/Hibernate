package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;

    public UserDaoHibernateImpl() {

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

        session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createSQLQuery(sqlCreateTable).executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        String sqlDropTable = "DROP TABLE IF EXISTS`myfirstdb`.`users`;";

        session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createSQLQuery(sqlDropTable).executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        session = Util.getSessionFactory().openSession();
       Transaction transaction = session.beginTransaction();

       session.save(user);

       transaction.commit();
       session.close();

        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(session.load(User.class, id));
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        users = session.createCriteria(User.class).list();

        transaction.commit();
        session.close();

        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE `myfirstdb`.`users`;";

        session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createSQLQuery(sql).executeUpdate();

        transaction.commit();
        session.close();
    }
}
