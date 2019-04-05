package ro.sda.dealership.storage;


import ro.sda.dealership.common.Utils;
import ro.sda.dealership.model.Car;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {

    private Connection conn = null;
    private Statement stmt = null;

    //initializare conexiune cu BD
    public void initialize() {
        try {
            //initializare driver -- pentru versiuni mai vechi se face
            //atentie la constante
            Class.forName(Utils.JDBC_DRIVER);

            conn = DriverManager.getConnection(
                    Utils.DB_URL,
                    Utils.USER,
                    Utils.PASS
            );
            System.out.println("Creating a statement...");
            stmt = conn.createStatement();
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addCar(Car car) {
        try {
            int rows = stmt.executeUpdate(
                    "insert into car(id, color, model )" +
                            "values(" + car.getId() + ", '" + car.getCarColor() + "', '" + car.getCarModel() + "')"
            );
            System.out.println("Rows inserted = " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> findAll() {
        List<Car> cars = new ArrayList<Car>();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("select * from car");
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getLong("id"));
                car.setCarColor(rs.getString("color"));
                car.setCarModel(rs.getString("model"));
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public Car findById(Long id) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("select * from car where id = " + id);
            if (rs.next()) {
                Car car = new Car();
                car.setId(rs.getLong("id"));
                car.setCarColor(rs.getString("color"));
                car.setCarModel(rs.getString("model"));
                car.setPriceCar(rs.getDouble("price"));
                return car;
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return null;
    }

    public void update(Car car) {
        int rows = 0;
        try {
            rows = stmt.executeUpdate("update car set color = '" + car.getCarColor() + "', model = '" + car.getCarModel() + "', price =  " + car.getCarPrice() + " where id = " + car.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("updated rows: " + rows);
    }

    public void deleteAll() {
       /* List<Car> cars = findAll();
        for (Car car : cars) {
            deleteById(car.getId());
        }*/
        int rows = 0;
        try {
            rows = stmt.executeUpdate("delete from car");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("deleted rows: " + rows);
    }

    public void deleteById(Long id) {
        int rows = 0;
        try {
            rows = stmt.executeUpdate("delete from car where id = " + id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("deleted rows: " + rows);

    }
    //metode in plus
}
