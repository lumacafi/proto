package br.com.proto.persistance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class PersistanceAdapter {

    private static final Logger LOGGER = LogManager.getLogger();


    @SuppressWarnings("unchecked")
    public <T> List<T> getList(Class klass) {
        LOGGER.debug("Getting "+ klass.getSimpleName() + " list");
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(klass);
            criteria.from(klass);
            List<T> list = session.createQuery(criteria).getResultList();
            tr.commit();
            sessionFactory.close();
            LOGGER.debug("Finished getting "+ klass.getSimpleName() + " list");
            return list;
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return new ArrayList<>();
    }


    @SuppressWarnings("unchecked")
    public  <T> T read(final String id, Class klass) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        LOGGER.debug("Reading " + klass.getSimpleName());
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            Object o = session.get(klass, id);
            tr.commit();
            sessionFactory.close();
            LOGGER.debug("Finished Reading " + klass.getSimpleName());
            return (T) o;
        } catch (HibernateException e) {
            LOGGER.error(e);
        }

        return null;
    }


    public boolean update(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Invalid object");
        }
        LOGGER.debug("Updating " + object.getClass().getSimpleName());
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            session.saveOrUpdate(object);
            tr.commit();
            sessionFactory.close();
        } catch (HibernateException e) {
            LOGGER.error(e);
            return false;
        }
        LOGGER.debug("Finished updating " + object.getClass().getSimpleName());
        return true;

    }

    public boolean delete(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Invalid object");
        }

        LOGGER.debug("Deleting " + object.getClass().getSimpleName());
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            session.delete(object);
            tr.commit();
            sessionFactory.close();
        } catch (HibernateException e) {
            LOGGER.error(e);
            return false;
        }
        LOGGER.debug("Finished deleting " + object.getClass().getSimpleName());
        return true;
    }

    public boolean save(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Invalid object");
        }

        LOGGER.debug("Saving " + object.getClass().getSimpleName());
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            session.save(object);
            tr.commit();
            sessionFactory.close();
        } catch (HibernateException e) {
            LOGGER.error(e);
            return false;
        }
        LOGGER.debug("Finished saving " + object.getClass().getSimpleName());
        return true;
    }
}
