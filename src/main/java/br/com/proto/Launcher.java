package br.com.proto;

import br.com.proto.entities.Client;
import br.com.proto.entities.ClientType;
import br.com.proto.entities.Contract;
import br.com.proto.entities.Service;
import br.com.proto.managers.ClientManager;
import br.com.proto.managers.ContractManager;
import br.com.proto.managers.ServiceManager;

import java.util.List;

public class Launcher {

    public static void main(String[] args) {
//        ClientManager clientManager = new ClientManager();
//        ServiceManager serviceManager = new ServiceManager();
//        ContractManager contractManager = new ContractManager();
//
//
//
//
//        Service service = serviceManager.create("programação", "criação de prototipo",150);
//        Client client = clientManager.create("Rambo", ClientType.SILVER);
//
//
//        Contract contract = contractManager.create(client, service, "hoje", "amanhã");
//
//        System.out.println(service.getValue());
//        System.out.println(contract.getValue());


        WebServer.start();

//        WebServer.stop();

    }
}
