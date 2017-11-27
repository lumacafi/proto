package br.com.proto.handlers;

import br.com.proto.entities.ClientType;
import br.com.proto.managers.ClientManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("clientregister")
public class ClientRegisterHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @POST
    public Response register(@FormParam("name") final String name,
                             @FormParam("type") final String typeString) {

        if (name == null || typeString == null) {
            return Response.seeOther(URI.create("/clientreg-error.html")).build();
        } else {
            ClientType clientType = ClientType.valueOf(typeString);
            ClientManager clientManager = new ClientManager();
            try {
                clientManager.create(name, clientType);
            } catch (Exception e) {
                LOGGER.error(e);
                return Response.seeOther(URI.create("/clientreg-error.html")).build();
            }
            return Response.seeOther(URI.create("/clientreg-sucess.html")).build();
        }
    }
}
