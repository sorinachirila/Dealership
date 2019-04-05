package ro.sda.dealership.presentation;

import ro.sda.dealership.model.Car;
import ro.sda.dealership.model.Stock;
import ro.sda.dealership.service.CarService;
import ro.sda.dealership.storage.CarDAO;

import java.util.Scanner;

public class StockReader implements ConsoleReader<Stock> {
    //private CarDAO carDAO = new CarDAO();
    private CarService carService = new CarService();

    public Stock read() {
        if (carService.getAllCars().isEmpty()) {
            return null;
        }
        Stock stock = new Stock();
        Car car;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Select car id for stock to be added: ");
            Long carID = ConsoleUtil.readLong("Car");
            car = carService.findById(carID);
        } while (car == null);
        System.out.println("Car quantity: ");
        Integer quantity = scanner.nextInt();
        System.out.println("Car location: ");
        String location = scanner.nextLine();
        stock.setCar(car);
        stock.setQuantity(quantity);
        stock.setLocation(location);
        return stock;
    }
}