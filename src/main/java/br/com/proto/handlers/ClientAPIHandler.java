package br.com.proto.handlers;

import br.com.proto.entities.Client;
import br.com.proto.managers.ClientManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("clients")
public class ClientAPIHandler {

    ClientManager clientManager = new ClientManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> doGet() {
        return clientManager.getList();
    }


    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHTML() {

        StringBuilder html = new StringBuilder();
        html.append("<html>");
        for (Client client : clientManager.getList()) {
            html.append(client.getName());
            html.append("<br>");
        }
        html.append("</html>");
        return html.toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Client getById(@PathParam("id") final String id) {
        return clientManager.read(id);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Client addClient(final Client client) {
        return clientManager.create(client);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Client updateClient(final Client client) {
        return clientManager.update(client);
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteClient(@PathParam("id") final String id) {
        boolean deleted = clientManager.delete(id);
        final String json = "{\"deleted\":" + deleted + " }";
        return Response.ok(json).build();
    }
}
