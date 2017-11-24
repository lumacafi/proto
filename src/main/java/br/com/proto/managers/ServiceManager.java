package br.com.proto.managers;

import br.com.proto.entities.Service;
import br.com.proto.persistance.PersistanceAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ServiceManager implements IManager{

    private static final Logger LOGGER = LogManager.getLogger();

    PersistanceAdapter persistance = new PersistanceAdapter();

    @SuppressWarnings("unchecked")
    @Override
    public <T> T create(String name, Object object) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }

        if (object == null) {
            throw new IllegalArgumentException("Invalid description");
        }

        Service service = new Service();
        service.setName(name);
        service.setDescription((String) object);

        boolean saved = persistance.save(service);
        if (!saved) {
            throw new IllegalStateException("Unable to save service");
        }
        return (T) service;

    }

    @Override
    public <T> T read(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        return persistance.read(id, Service.class);
    }

    @Override
    public <T> List<T> getList() {
        LOGGER.debug("Getting service list");
        return persistance.getList(Service.class);
    }

    @Override
    public boolean delete(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Invalid service");
        }
        return persistance.delete(object);

    }

    @Override
    public boolean delete(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        Service service = read(id);
        return delete(service);

    }

    @Override
    public boolean update(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Invalid service");
        }
        return persistance.update(object);
    }
}
