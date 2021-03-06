package br.com.proto.managers;

import br.com.proto.entities.Client;
import br.com.proto.entities.ClientType;
import br.com.proto.entities.Contract;
import br.com.proto.entities.Service;
import br.com.proto.persistance.PersistanceAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ContractManager {

    private static final Logger LOGGER = LogManager.getLogger();

    PersistanceAdapter persistance = new PersistanceAdapter();


    public double getCalculatedContractValue(final String clientId, final String serviceId, final Date startDate, final Date endDate) {
        Contract contract = new Contract();
        ClientManager clientManager = new ClientManager();
        ServiceManager serviceManager = new ServiceManager();
        contract.setClient(clientManager.read(clientId));
        contract.setService(serviceManager.read(serviceId));
        contract.setStartDate(startDate);
        contract.setEndDate(endDate);
        double cval = calculateContractValue(contract);
        contract.setValue(cval);
        return contract.getValue();
    }

    public Contract create(final String clientId, final String serviceId, final Date startDate, final Date endDate) {
        if (serviceId == null) {
            throw new IllegalArgumentException("Invalid service id");
        }

        if (clientId == null) {
            throw new IllegalArgumentException("Invalid client id");
        }

        ClientManager clientManager = new ClientManager();
        ServiceManager serviceManager = new ServiceManager();
        Client client = clientManager.read(clientId);
        Service service = serviceManager.read(serviceId);
        return create(client, service, startDate, endDate);
    }

    public Contract create(final Client client, final Service service, final Date startDate, final Date endDate) {
        Contract contract = new Contract();
        contract.setClient(client);
        contract.setService(service);
        contract.setStartDate(startDate);
        contract.setEndDate(endDate);
        double cval = calculateContractValue(contract);
        contract.setValue(cval);
        int remaining = calculateRemainingDays(endDate);
        contract.setDaysRemaining(remaining);
        boolean saved = persistance.save(contract);
        if (!saved) {
            throw new IllegalStateException("Unable to save contract");
        }
        return contract;
    }

    private int calculateRemainingDays(Date endDate) {
        if (endDate == null) {
            throw new IllegalArgumentException("Invalid date");
        }

        LocalDate endLocalDate = Instant.ofEpochMilli(endDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        long days = ChronoUnit.DAYS.between(LocalDate.now(), endLocalDate);
        return (int) days;
    }


    public Contract read(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        return persistance.read(id, Contract.class);
    }

    public List<Contract> getList() {
        LOGGER.debug("Getting contract list");
        return persistance.getList(Contract.class);
    }

    public List<Contract> getListByClientId(final String clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("Invalid client id");
        }
        List<Contract> filteredList;
        LOGGER.debug("Getting contract list by client id: ");
        List<Contract> list = getList();
        filteredList = list.stream()
                .filter(contract -> contract.getClient().getId().equals(clientId))
                .collect(Collectors.toList());
        LOGGER.debug("Finished Getting contract list by client id: ");
        return filteredList;
    }

    public boolean delete(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Invalid contract");
        }
        return persistance.delete(object);
    }

    public boolean delete(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        Contract contract = read(id);
        return delete(contract);

    }

    public boolean update(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Invalid contract");
        }
        return persistance.update(object);
    }

    //##################################################################################################################
    //##################################################################################################################
    //##################################################################################################################
    //##################################################################################################################


    private double calculateContractValue(Contract contract) {
        if (contract == null || contract.getClient() == null || contract.getService() == null) {
            throw new IllegalArgumentException("Invalid contract");
        }
        LOGGER.debug("Calculating contract discount value");
        ClientType type = contract.getClient().getType();
        double value = contract.getService().getValue();
        double clientTypeDiscountedValue = getClientTypeDiscountedValue(value, type);
        LOGGER.debug("Finished calculating contract discount value");
        return clientTypeDiscountedValue;
    }


    private double getClientTypeDiscountedValue(final double value, final ClientType type) {
        double discountedValue = -1;
        switch (type) {
            case GOLD:
                discountedValue = value - calculatePercentage(10L, value);
                break;
            case SILVER:
                discountedValue = value - calculatePercentage(5L, value);
                break;
        }
        return discountedValue;
    }

    private double calculatePercentage(final double a, final double b) {
        return a * b / 100;
    }

}
