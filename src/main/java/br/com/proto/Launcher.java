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
        ClientManager clientManager = new ClientManager();
        ServiceManager serviceManager = new ServiceManager();
        ContractManager contractManager = new ContractManager();


//        List<Contract> list = contractManager.getList();
//        for (Contract contract : list) {
//            contractManager.delete(contract);
//        }


//        List<Service> list = serviceManager.getList();
//        for (Service service : list) {
//            serviceManager.delete(service);
//        }


//        List<Client> list = clientManager.getList();
//        for (Client client : list) {
//            clientManager.delete(client);
//
//        }

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
