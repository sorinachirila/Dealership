package ro.sda.dealership.storage;

import ro.sda.dealership.common.Utils;
import ro.sda.dealership.model.Car;
import ro.sda.dealership.model.Stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {

    private Connection conn1;
    private Statement stmt1;
    private CarDAO carDAO;

    public StockDAO() {
        conn1 = null;
        stmt1 = null;
        carDAO = new CarDAO();
        carDAO.initialize();
    }

    public void initialize() {
        try {
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

    public void addStock(Stock stock) {
        try {
            int rows = stmt1.executeUpdate(
                    "insert into stock(id, quantity, location)" +
                            "values(" + stock.getId() + ", " + stock.getQuantity() + ", '" + stock.getLocation() + "', )"
            );
            System.out.println("Rows inserted = " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Stock> findAll() {
        List<Stock> stocks = new ArrayList<Stock>();
        ResultSet rs = null;
        try {
            rs = stmt1.executeQuery("select * from stock");
            while (rs.next()) {
                Stock stock = new Stock();
                stock.setId(rs.getLong("id"));
                stock.setQuantity(rs.getInt("quantity"));
                stock.setLocation(rs.getString("location"));
                Car car = carDAO.findById(rs.getLong("car_id"));
                stock.setCar(car);
                stocks.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    public Stock findById(Long id) {
        ResultSet rs = null;
        try {
            rs = stmt1.executeQuery("select * from stock where id = " + id);
            if (rs.next()) {
                Stock stock = new Stock();
                stock.setId(rs.getLong("id"));
                stock.setQuantity(rs.getInt("quantity"));
                stock.setLocation(rs.getString("location"));
                Car car = carDAO.findById(rs.getLong("car_id"));
                return stock;
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return null;
    }

    public void updateQuantity(Stock stock) {
        int rows = 0;
        try {
            rows = stmt1.executeUpdate("update stock set quantity = " + stock.getQuantity() + " where id = " + stock.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("updated rows: " + rows);
    }

    public void deleteAll() {
        int rows = 0;
        try {
            rows = stmt1.executeUpdate("delete from stock");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("deleted rows: " + rows);
    }

    public void deleteById(Long id) {
        int rows = 0;
        try {
            rows = stmt1.executeUpdate("delete from stock where id = " + id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("deleted rows: " + rows);

    }

//    public Stock findByCarIdAndLocation(Long id, String location) {
//        for (Stock stock : getItems()) {
//            if (stock.getCar().getId().equals(id) && stock.getLocation().equals(location)) {
//                return stock;
//            }
//        }
//        return null;
//    }
}