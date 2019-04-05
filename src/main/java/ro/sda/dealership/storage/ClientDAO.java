package ro.sda.dealership.storage;

import ro.sda.dealership.common.Utils;
import ro.sda.dealership.model.Client;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    private Connection conn1 = null;
    private Statement stmt1 = null;

    //initializare conexiune cu BD
    public void initialize() {
        try {
            //initializare driver -- pentru versiuni mai vechi se face
            //atentie la constante
            Class.forName(Utils.JDBC_DRIVER);

            conn1 = DriverManager.getConnection(
                    Utils.DB_URL,
                    Utils.USER,
                    Utils.PASS
            );
            System.out.println("Creating a statement...");
            stmt1 = conn1.createStatement();
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addClient(Client client) {
        try {
            int rows = stmt1.executeUpdate(
                    "insert into client(id, name, phone )" +
                            "values(" + client.getId() + ", '" + client.getName() + "', '" + client.getPhoneNumber() + "')"
            );
            System.out.println("Rows inserted = " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> findAll() {
        List<Client> clients = new ArrayList<Client>();
        ResultSet rs = null;
        try {
            rs = stmt1.executeQuery("select * from client");
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));
                client.setPhoneNumber(rs.getString("phone"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public Client findById(Long id) {
        ResultSet rs = null;
        try {
            rs = stmt1.executeQuery("select * from client where id = " + id);
            if (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));
                client.setPhoneNumber(rs.getString("phone"));
                return client;
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return null;
    }

    public void update(Client client) {
        int rows = 0;
        try {
            rows = stmt1.executeUpdate("update client set name = '" + client.getName() + "', phone = '" + client.getPhoneNumber() + "' where id = " + client.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("updated rows: " + rows);
    }

    public void deleteAll() {
        int rows = 0;
        try {
            rows = stmt1.executeUpdate("delete from client");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("deleted rows: " + rows);
    }

    public void deleteById(Long id) {
        int rows = 0;
        try {
            rows = stmt1.executeUpdate("delete from client where id = " + id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("deleted rows: " + rows);

    }
    //metode in plus
}
