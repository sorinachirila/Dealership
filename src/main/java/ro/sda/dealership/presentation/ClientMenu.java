package ro.sda.dealership.presentation;

import ro.sda.dealership.model.Client;
import ro.sda.dealership.model.Order;
import ro.sda.dealership.service.ClientService;

import java.util.Scanner;

public class ClientMenu extends AbstractMenu {
    //private ClientDAO clientDAO = new ClientDAO();
    private ClientService clientService;
    private ClientReader reader;
    private ClientWriter writer;

    public ClientMenu() {
        clientService = new ClientService();
        reader = new ClientReader();
        writer = new ClientWriter();
        clientService.initializeDAO();
    }

    protected void displayOption() {
        System.out.println("1.View all clients");
        System.out.println("2.View client details");
        System.out.println("3.Edit client");
        System.out.println("4.Add new client");
        System.out.println("5.Delete client");
        //System.out.println("6.Search client");
        System.out.println("0.Exit");
    }

    protected void executeComand(Integer option) {
        switch (option) {
            case 1:
                writer.writeAll(clientService.getAllClients());
                break;
            case 2:
                if (clientService.getAllClients().isEmpty()) {
                    System.out.println("No clients available.");
                } else {
                    writer.writeAll(clientService.getAllClients());
                    System.out.println("Select client to view: ");
                    displayClientDetails();
                }
                break;
            case 3:
                if (clientService.getAllClients().isEmpty()) {
                    System.out.println("No clients available.");
                } else {
                    writer.writeAll(clientService.getAllClients());
                    System.out.print("Select client to edit: ");
                    editClient();
                }
                break;
            case 4:
                Client newClient = reader.read();
                clientService.saveClient(newClient);
                System.out.println("Client added");
                break;
            case 5:
                if (clientService.getAllClients().isEmpty()) {
                    System.out.println("No clients available.");
                } else {
                    writer.writeAll(clientService.getAllClients());
                    System.out.print("Select a client to delete: ");
                    Long id = ConsoleUtil.readLong("Client");
                    clientService.deleteById(id);
                }
                break;
            case 0:
                System.out.println("Exiting to Main menu");
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private void editClient() {
        Scanner scanner = new Scanner(System.in);
        Client foundClient = clientService.findById(ConsoleUtil.readLong("Client"));
        if (foundClient == null) {
            System.out.println("Client not found");
        } else {
            System.out.println("Enter the new name: ");
            foundClient.setName(scanner.nextLine());
            System.out.println("Enter the new phone number: ");
            String phoneNumber = scanner.nextLine().trim();
            foundClient.setPhoneNumber(phoneNumber);
            /*System.out.print("Enter new address: ");
            foundClient.setAddress(scanner.nextLine());*/
            clientService.updateClient(foundClient);
            System.out.println("Client updated");
        }
    }

    private void displayClientDetails() {
        Client foundClient = clientService.findById(ConsoleUtil.readLong("Client"));
        if (foundClient == null) {
            System.out.println("Client not found");
        } else {
            System.out.println("Client details are: ");
            writer.write(foundClient);
        }
    }

/*
Your option is:
1
Clients list:
Id: 2 , Name: Popescu , Phone number: 0232222444
Id: 3 , Name: Ionescu , Phone number: 0755489645
1.View all clients
2.View client details
3.Edit client
4.Add new client
5.Delete client
0.Exit
Your option is:


STRUCTURA tabela client
id, name, phone

DDL
CREATE TABLE `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
*/


 /*   public static void main(String[] args) {
        ClientService service = new ClientService();
        service.initializeDAO();

        Client newClient = new Client();
        newClient.setId(3L);
        newClient.setName("Ionescu");
        newClient.setPhoneNumber("0755489645");
        service.saveClient(newClient);
    }*/
}

