package br.com.proto.handlers;

import br.com.proto.entities.Contract;
import br.com.proto.managers.ContractManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("contract")
public class ContractHandler {


    private ContractManager contractManager = new ContractManager();

    private static final Logger LOGGER = LogManager.getLogger();

    String pattern = "dd/MM/yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Contract> doGet(@QueryParam("clid") final String clientId) {
        if (clientId != null) {
            return contractManager.getListByClientId(clientId);
        } else {
            return null;
        }
    }


    @POST
    public Response doPost(
            @FormParam("clid") final String clientId,
            @FormParam("startdate") final String startDateString,
            @FormParam("enddate") final String endDateString,
            @FormParam("service") final String serviceId,
            @FormParam("val") final String value) {


        Date startDate = null;
        Date endDate = null;
        if (startDateString != null) {
            try {
                startDate = simpleDateFormat.parse(startDateString);
            } catch (ParseException e) {
                LOGGER.error(e);
                return Response.seeOther(URI.create("/contractreg-error.html")).build();
            }
        }

        if (endDateString != null) {
            try {
                endDate = simpleDateFormat.parse(endDateString);
            } catch (ParseException e) {
                LOGGER.error(e);
                return Response.seeOther(URI.create("/contractreg-error.html")).build();
            }
        }



        if (value == null) {
            double calculatedContractValue = contractManager.getCalculatedContractValue(clientId, serviceId, startDate, endDate);
            return Response.seeOther(URI.create(
                    "/contractreg.html?clid=" + clientId
                            + "&val=" + calculatedContractValue
                            + "&sdate="+simpleDateFormat.format(startDate)
                            + "&edate="+simpleDateFormat.format(endDate)
            )).build();
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
