package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;
    private final static Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    public UserDaoHibernateImpl() {
        session = Util.getSession();
    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("""
                CREATE TABLE `user` (
                `id` INT NOT NULL AUTO_INCREMENT,
                `name` VARCHAR(45) NOT NULL,
                `lastName` VARCHAR(45) NOT NULL,
                `age` TINYINT NOT NULL,
                PRIMARY KEY (`id`));""").executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            transaction.rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE user").executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            transaction.rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.info(ex.getMessage());
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE User WHERE id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.info(ex.getMessage());
        }

    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from User");
            transaction.commit();
            return query.getResultList();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql = new String("delete from User");
            session.createQuery(hql).executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.info(ex.getMessage());
        }
    }
}
