package br.com.proto;

import br.com.proto.entities.Client;
import br.com.proto.managers.ClientManager;

import java.util.List;

public class Launcher {

    public static void main(String[] args) {

        ClientManager clientManager = new ClientManager();
//        Client client = clientManager.createClient("bla", ClientType.BRONZE);
//        System.out.println(client.getId());
//        Client loadedClient = clientManager.readClient(client.getId());
//        System.out.println(loadedClient.getType());
//        System.out.println(loadedClient.getId());
//
//        boolean b = clientManager.deleteClient(client.getId());
//        System.out.println(b);


        List<Client> clientList = clientManager.getClientList();
        for (Client client : clientList) {
            System.out.println(client.getId());
        }

//        WebServer.start();

//        WebServer.stop();

    }
}
