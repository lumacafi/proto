package br.com.proto.managers;

import br.com.proto.entities.Client;
import br.com.proto.entities.ClientType;
import br.com.proto.persistance.PersistanceAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ClientManager implements IManager {

    private static final Logger LOGGER = LogManager.getLogger();

    PersistanceAdapter persistance = new PersistanceAdapter();


    @SuppressWarnings("unchecked")
    @Override
    public <T> T create(String name, Object object) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }

        if (object == null) {
            throw new IllegalArgumentException("Invalid type");
        }

        Client client = new Client();
        client.setName(name);
        client.setType((ClientType) object);

        boolean saved = persistance.save(client);
        if (!saved) {
            throw new IllegalStateException("Unable to save client");
        }
        return (T) client;
    }

    @Override
    public <T> T read(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        return persistance.read(id, Client.class);
    }

    @Override
    public <T> List<T> getList() {
        LOGGER.debug("Getting client list");
        return persistance.getList(Client.class);
    }

    @Override
    public boolean delete(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Invalid client");
        }
        return persistance.delete(object);
    }

    @Override
    public boolean delete(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        Client client = read(id);
        return delete(client);
    }

    @Override
    public boolean update(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Invalid client");
        }
        return persistance.update(object);
    }
}
