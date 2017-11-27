package br.com.proto.handlers;


import br.com.proto.entities.Client;
import br.com.proto.managers.ClientManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("clientlist")
public class ClientListHandler {

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<Client> doGet() {
        ClientManager clientManager = new ClientManager();
        List<Client> list = clientManager.getList();

        return list;

    }
}
