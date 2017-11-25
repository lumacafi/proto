package br.com.proto.managers;

import br.com.proto.entities.Service;
import br.com.proto.persistance.PersistanceAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ServiceManager {

    private static final Logger LOGGER = LogManager.getLogger();

    PersistanceAdapter persistance = new PersistanceAdapter();

    @SuppressWarnings("unchecked")
    public Service create(String name, final String description, final double value) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }

        if (description == null) {
            throw new IllegalArgumentException("Invalid description");
        }

        if (value <= 0) {
            throw new IllegalArgumentException("Invalid value");
        }

        Service service = new Service();
        service.setName(name);
        service.setDescription(description);
        service.setValue(value);

        boolean saved = persistance.save(service);
        if (!saved) {
            throw new IllegalStateException("Unable to save service");
        }
        return service;
    }

    public Service create(final Service service) {
        if (service == null) {
            throw new IllegalArgumentException("Invalid service");
        }

        boolean saved = persistance.save(service);
        if (!saved) {
            throw new IllegalStateException("Unable to save service");
        }
        return service;

    }

    public Service read(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        return persistance.read(id, Service.class);
    }

    public List<Service> getList() {
        LOGGER.debug("Getting service list");
        return persistance.getList(Service.class);
    }

    public boolean delete(Object object) {
        if (object == null) {
            return false;
        }
        return persistance.delete(object);
    }

    public boolean delete(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        Service service = read(id);
        return delete(service);
    }

    public Service update(Service service) {
        if (service == null) {
            throw new IllegalArgumentException("Invalid service");
        }
        Service loaded = read(service.getId());
        if (loaded != null) {
            return persistance.update(service);
        } else {
            return null;
        }
    }
}
