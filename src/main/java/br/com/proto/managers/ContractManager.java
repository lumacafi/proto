package br.com.proto.managers;

import br.com.proto.entities.Client;
import br.com.proto.entities.ClientType;
import br.com.proto.entities.Contract;
import br.com.proto.entities.Service;
import br.com.proto.persistance.PersistanceAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ContractManager {

    private static final Logger LOGGER = LogManager.getLogger();

    PersistanceAdapter persistance = new PersistanceAdapter();

    @SuppressWarnings("unchecked")
    public <T> T create(final Client client, final Service service, final String startDate, final String endDate) {
        Contract contract = new Contract();
        contract.setClient(client);
        contract.setService(service);
        contract.setStartDate(startDate);
        contract.setEndDate(endDate);
        double cval = calculateContractValue(contract);
        contract.setValue(cval);

        boolean saved = persistance.save(contract);
        if (!saved) {
            throw new IllegalStateException("Unable to save contract");
        }
        return (T) contract;
    }


    public <T> T read(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        return persistance.read(id, Contract.class);
    }

    public <T> List<T> getList() {
        LOGGER.debug("Getting contract list");
        return persistance.getList(Contract.class);
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
        Contract contract= read(id);
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
