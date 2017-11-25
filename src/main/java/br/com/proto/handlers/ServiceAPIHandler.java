package br.com.proto.handlers;

import br.com.proto.entities.Service;
import br.com.proto.managers.ServiceManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("services")
public class ServiceAPIHandler {

    ServiceManager serviceManager = new ServiceManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Service> doGet() {
        return serviceManager.getList();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Service getById(@PathParam("id") final String id) {
        return serviceManager.read(id);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Service addService(final Service service) {
        return serviceManager.create(service);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Service updateService(final Service service) {
        return serviceManager.update(service);
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteService(@PathParam("id") final String id) {
        boolean deleted = serviceManager.delete(id);
        final String json = "{\"deleted\":" + deleted + " }";
        return Response.ok(json).build();
    }
}
