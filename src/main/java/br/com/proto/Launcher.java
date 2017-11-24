package br.com.proto;

import br.com.proto.entities.Client;
import br.com.proto.entities.ClientType;
import br.com.proto.managers.ClientManager;

import java.util.List;

public class Launcher {

    public static void main(String[] args) {
        ClientManager clientManager = new ClientManager();


        Client client = clientManager.create("bla", ClientType.BRONZE);
//        System.out.println(client.getId());
//        Client loadedClient = clientManager.readClient(client.getId());
//        System.out.println(loadedClient.getType());
//        System.out.println(loadedClient.getId());
//
//        boolean b = clientManager.deleteClient(client.getId());
//        System.out.println(b);


        List<Client> clientList = clientManager.getList();
        for (Client cl : clientList) {
            System.out.println(cl.getId() + " " + cl.getName());
//            cl.setName("xpto");
//            boolean updated = clientManager.update(cl);
//            System.out.println(updated);
//            clientManager.deleteClient(cl);
        }

//        WebServer.start();

//        WebServer.stop();

    }
}
