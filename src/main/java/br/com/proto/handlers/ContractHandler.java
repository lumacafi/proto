package br.com.proto.handlers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("contract")
public class ContractHandler {


    @GET
    public Response doGet() {
        return Response.ok(123).build();
    }
}
