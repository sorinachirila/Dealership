package ro.sda.dealership.service;

import ro.sda.dealership.model.Client;
import ro.sda.dealership.model.Order;
import ro.sda.dealership.storage.ClientDAO;

import java.util.List;

public class ClientService {
    private ClientDAO clientDAO;

    public ClientService() {
        clientDAO = new ClientDAO();
    }

    public void initializeDAO() {
        clientDAO.initialize();
    }

    public List<Client> getAllClients() {
        return clientDAO.findAll();
    }

    public Client getClient(Long id) {
        return clientDAO.findById(id);
    }

    public void saveClient(Client client) {
        if (client.getId() != null) {
            clientDAO.addClient(client);
        }
    }

    public void deleteById(Long id) {
        clientDAO.deleteById(id);
    }

    public void updateClient(Client client) {
        clientDAO.update(client);
    }

    public Client findById(Long id) {
        return clientDAO.findById(id);
    }
}


