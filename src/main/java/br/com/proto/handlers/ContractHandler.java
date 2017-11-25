package br.com.proto.handlers;

import br.com.proto.entities.Contract;
import br.com.proto.managers.ContractManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("contract")
public class ContractHandler {


    private ContractManager contractManager = new ContractManager();

    @GET
    public List<Contract> doGet(@QueryParam("clid") final String clientId) {
        if (clientId != null) {
            List<Contract> list = contractManager.getListByClientId(clientId);
            for (Contract contract : list) {
                System.out.println(contract.getId());
            }
            return list;
        } else {
            return null;
        }
    }


    @POST
    public Response doAdd(@FormParam("clid") final String clientId) {
        return null;
    }
}
