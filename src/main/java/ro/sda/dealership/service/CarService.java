package ro.sda.dealership.service;

import ro.sda.dealership.storage.CarDAO;
import ro.sda.dealership.model.Car;

import java.util.List;

public class CarService {
    private CarDAO carDAO;

    public CarService() {
        carDAO = new CarDAO();

    }

    //conexiune cu BD care sa fie functionala
    public void initializeDAO() {
        carDAO.initialize();
    }

    public List<Car> getAllCars() {
        return carDAO.findAll();
    }

    public Car getCar(Long id) {
        return carDAO.findById(id);
    }

    public void save(Car car) {
        if (car.getId() != null) {
            carDAO.addCar(car);
        }
    }

    public void deleteById(Long id) {
        carDAO.deleteById(id);
    }

    public void update(Car car) {
        carDAO.update(car);
    }

    public Car findById(Long id) {
        return carDAO.findById(id);
    }
}
