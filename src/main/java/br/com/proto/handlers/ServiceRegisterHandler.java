package br.com.proto.handlers;

import br.com.proto.entities.Service;
import br.com.proto.managers.ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("serviceregister")
public class ServiceRegisterHandler {


    private static final Logger LOGGER = LogManager.getLogger();

    @POST
    public Response register(@FormParam("name") final String name,
                             @FormParam("description") final String description,
                             @FormParam("value") final double value) {

        ServiceManager serviceManager = new ServiceManager();

        try {
            Service service = serviceManager.create(name, description, value);
            return Response.seeOther(URI.create("/servicereg-sucess.html")).build();
        } catch (Exception e) {
            LOGGER.error(e);
            return Response.seeOther(URI.create("/servicereg-error.html")).build();
        }
    }
}
