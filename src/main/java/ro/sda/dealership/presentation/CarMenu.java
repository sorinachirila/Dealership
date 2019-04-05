package ro.sda.dealership.presentation;

import ro.sda.dealership.model.Car;
import ro.sda.dealership.service.CarService;
import ro.sda.dealership.storage.CarDAO;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CarMenu extends AbstractMenu {
    //private CarDAO carDAO = new CarDAO();
    private CarService carService;
    private CarReader reader;
    private CarWriter writer;

    public CarMenu() {
        carService = new CarService();
        reader = new CarReader();
        writer = new CarWriter();
        carService.initializeDAO();
    }

    protected void displayOption() {
        System.out.println("1. View all cars");
        System.out.println("2. View car details");
        System.out.println("3. Edit car");
        System.out.println("4. Add new car");
        System.out.println("5. Delete car");
        System.out.println("0. Exit");
    }

    protected void executeComand(Integer option) {
        switch (option) {
            case 1:
                writer.writeAll(carService.getAllCars());
                break;
            case 2:
                if (carService.getAllCars().isEmpty()) {
                    System.out.println("No cars available.");
                } else {
                    writer.writeAll(carService.getAllCars());
                    System.out.print("Select car to view: ");
                    displayCarDetails();
                }
                break;
            case 3:
                if (carService.getAllCars().isEmpty()) {
                    System.out.println("No cars available.");
                } else {
                    writer.writeAll(carService.getAllCars());
                    System.out.print("Select car to edit: ");
                    editCar();
                }
                break;
            case 4:
                Car newCar = reader.read();
                carService.save(newCar);
                break;
            case 5:
                if (carService.getAllCars().isEmpty()) {
                    System.out.println("No cars available.");
                } else {
                    writer.writeAll(carService.getAllCars());
                    System.out.print("Select car to delete: ");
                    Long id = ConsoleUtil.readLong("Car");
                    carService.deleteById(id);
                }
            case 0:
                System.out.println("Exiting to Main menu");
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private void editCar() {
        System.out.println("Select car to edit");
        Scanner scanner = new Scanner(System.in);
        Long id = ConsoleUtil.readLong("Car");
        Car foundCar = carService.findById(id);
        if (foundCar == null) {
            System.out.println("Car not found");
        } else {
            try {
                System.out.print("Enter new price: ");
                String priceS = scanner.nextLine();
                double price = Double.valueOf(priceS);
                //scanner.nextLine();
                foundCar.setPriceCar(price);
                System.out.println("Enter new model: ");
                String newModel = scanner.nextLine();
                foundCar.setCarModel(newModel);
                System.out.println("Enter new color: ");
                String newColor = scanner.nextLine();
                foundCar.setCarColor(newColor);
                carService.update(foundCar);
                System.out.println("Car updated");
            } catch (InputMismatchException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayCarDetails() {
        System.out.println("Choose car by Id: ");
        Long id = ConsoleUtil.readLong("Car");
        Car searchedCar = carService.findById(id);
        if (searchedCar == null) {
            System.out.println("Car not found");
        } else {
            System.out.println("Car details: ");
            writer.write(searchedCar);
        }
    }
//OUTPUT - rulare pentru OPTIUNEA 2
/*    Main Menu
1. Cars
2. Clients
3. Orders
4. Stocks
0. Exit
Your option is:
1
Cars menu
Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
Creating a statement...
1. View all cars
2. View car details
3. Edit car
4. Add new car
5. Delete car
0. Exit
Your option is:
2
Cars list:
Id: 1, Car model: Logan, Car color: Navy
Car price: 0.0
Id: 2, Car model: Nissan, Car color: Black
Car price: 0.0
Id: 3, Car model: Hyundai, Car color: Red
Car price: 0.0
Select car to view: Choose car by Id:

Car ID: 1
Car details:
Id: 1
Car model: Logan
Car color: Navy
Car price: 2000.5
1. View all cars
2. View car details
3. Edit car
4. Add new car
5. Delete car
0. Exit
Your option is:

STRUCTURA tabela car
id, model, color

DDL
CREATE TABLE `car` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `color` varchar(45) DEFAULT NULL,
  `model` varchar(45) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

 */

   /* public static void main(String[] args) {

        //CarService service = new CarService();
        carService.initializeDAO();

        Car newCar = new Car();
        newCar.setId(2L);
        newCar.setCarColor("rosie");
        newCar.setCarModel("Nissan");
        carService.save(newCar);

        //service.save(newCar);
        //List<Car> cars = service.getAllCars();
        //for(Car car: cars){
        //   System.out.println(car.getId() + ". Culoare: " + car.getCarColor() + ". Model: " + car.getCarModel() );
        //}
        //service.delete(newCar.getId());
        //System.out.println(service.getAllCars());
       // newCar.setCarColor("verde");
        //service.update(newCar);
        //System.out.println(service.getCar(newCar.getId()).getCarColor());
        //Car car = service.findById(2l);
        //System.out.println(car.getId() + ". Culoare: " + car.getCarColor() + ". Model: " + car.getCarModel());
        //service.delete(2l);

    }*/
}
