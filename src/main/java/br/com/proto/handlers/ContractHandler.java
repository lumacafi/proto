package br.com.proto.handlers;

import br.com.proto.entities.Contract;
import br.com.proto.managers.ContractManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
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
    public Response doPost(
            @FormParam("clid") final String clientId,
            @FormParam("startdate") final String startDate,
            @FormParam("enddate") final String endDate,
            @FormParam("service") final String serviceId,
            @FormParam("val") final String value) {


        if (value == null) {
            double calculatedContractValue = contractManager.getCalculatedContractValue(clientId, serviceId, startDate, endDate);
            return Response.seeOther(URI.create("/contractreg.html?clid=" + clientId + "&val=" + calculatedContractValue)).build();
        } else {
            Contract contract = contractManager.create(clientId, serviceId, startDate, endDate);
            if (contract != null) {
                return Response.seeOther(URI.create("/contractreg-sucess.html")).build();
            } else {
                return Response.seeOther(URI.create("/contractreg-error.html")).build();
            }

        }

    }

}
