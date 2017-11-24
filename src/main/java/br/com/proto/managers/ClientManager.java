package br.com.proto.managers;

import br.com.proto.entities.Client;
import br.com.proto.entities.ClientType;
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

public class ClientManager {

    private static final Logger LOGGER = LogManager.getLogger();


    public Client createClient(final String name, final ClientType type) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }

        if (type == null) {
            throw new IllegalArgumentException("Invalid type");
        }

        Client client = new Client();
        client.setName(name);
        client.setType(type);
        boolean saved = saveClient(client);
        if (!saved) {
            throw new IllegalStateException("Unable to save client");
        }
        return client;
    }


    public Client readClient(final String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        LOGGER.debug("Reading Client");
        Client client = null;
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            client = session.get(Client.class, id);
            tr.commit();
            sessionFactory.close();
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        LOGGER.debug("Finished Reading Client");
        return client;
    }

    public List<Client> getClientList() {
        LOGGER.debug("Getting client list");
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
            criteria.from(Client.class);
            List<Client> list = session.createQuery(criteria).getResultList();
            tr.commit();
            sessionFactory.close();
            LOGGER.debug("Finished Getting client list");
            return list;
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return new ArrayList<>();
    }

    public boolean deleteClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Invalid client");
        }

        LOGGER.debug("Deleting client");
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            session.delete(client);
            tr.commit();
            sessionFactory.close();
            return true;
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        LOGGER.debug("Finished Deleting client");
        return false;
    }


    public boolean deleteClient(final String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        Client client = readClient(id);
        return deleteClient(client);
    }

    //##################################################################################################################
    //##################################################################################################################
    //##################################################################################################################
    //##################################################################################################################
    //##################################################################################################################
    //##################################################################################################################


    private boolean saveClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Invalid client");
        }

        LOGGER.debug("Saving client");
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            session.save(client);
            tr.commit();
            sessionFactory.close();
        } catch (HibernateException e) {
            LOGGER.error(e);
            return false;
        }
        LOGGER.debug("Finished saving client");
        return true;
    }
}
