package br.com.proto.managers;

import br.com.proto.entities.Client;
import br.com.proto.entities.ClientType;
import br.com.proto.persistance.PersistanceAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ClientManager {

    private static final Logger LOGGER = LogManager.getLogger();

    PersistanceAdapter persistance = new PersistanceAdapter();


    public Client create(String name, ClientType type) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }

        if (type == null) {
            throw new IllegalArgumentException("Invalid type");
        }

        Client client = new Client();
        client.setName(name);
        client.setType(type);

        boolean saved = persistance.save(client);
        if (!saved) {
            throw new IllegalStateException("Unable to save client");
        }
        return client;
    }

    public Client create(final Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Invalid service");
        }

        boolean saved = persistance.save(client);
        if (!saved) {
            throw new IllegalStateException("Unable to save service");
        }
        return client;
    }


    public Client read(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        return persistance.read(id, Client.class);
    }

    public List<Client> getList() {
        LOGGER.debug("Getting client list");
        return persistance.getList(Client.class);
    }

    public boolean delete(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Invalid client");
        }
        return persistance.delete(object);
    }

    public boolean delete(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        Client client = read(id);
        return delete(client);
    }

    public Client update(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Invalid client");
        }

        Client loaded = read(client.getId());
        if (loaded != null) {
            return persistance.update(client);
        } else {
            return null;
        }
    }
}
