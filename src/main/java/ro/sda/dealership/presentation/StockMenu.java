package ro.sda.dealership.presentation;

import ro.sda.dealership.model.Stock;
import ro.sda.dealership.service.CarService;
import ro.sda.dealership.service.StockService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StockMenu extends AbstractMenu {
    //private StockDAO stockDAO = new StockDAO();
    private StockService stockService;
    private CarService carService;
    private StockReader reader;
    private StockWriter writer;

    public StockMenu() {
        stockService = new StockService();
        carService = new CarService();
        reader = new StockReader();
        writer = new StockWriter();
        stockService.initializeDAO();
    }

    protected void displayOption() {
        System.out.println("1. View all stocks");
        System.out.println("2. View stock details");
        System.out.println("3. Edit stock");
        System.out.println("4. Add new stock");
        System.out.println("5. Delete stock");
        System.out.println("0. Exit");
    }

    protected void executeComand(Integer option) {
        switch (option) {
            case 1:
                writer.writeAll(stockService.getAllStocks());
                break;
            case 2:
                if (stockService.getAllStocks().isEmpty()) {
                    System.out.println("No stocks available.");
                } else {
                    writer.writeAll(stockService.getAllStocks());
                    System.out.print("Select stock to view: ");
                    displayStockDetails();
                }
                break;
            case 3:
                if (stockService.getAllStocks().isEmpty()) {
                    System.out.println("No stocks available.");
                } else {
                    writer.writeAll(stockService.getAllStocks());
                    System.out.print("Select stock to edit: ");
                    editStock();
                }
                break;
            case 4:
                Stock newStock = reader.read();
                if (newStock == null) {
                    System.out.println("No cars available");
                } else {
                    stockService.save(newStock);
                    System.out.println("Stock added");
                }
                break;
            case 5:
                if (stockService.getAllStocks().isEmpty()) {
                    System.out.println("No stocks available.");
                } else {
                    writer.writeAll(stockService.getAllStocks());
                    System.out.print("Select stock to delete: ");
                    stockService.deleteById(ConsoleUtil.readLong("Stock"));
                }
                break;
            case 0:
                System.out.println("Exiting to Main menu");
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private void editStock() {
        System.out.println("Select by id a stock to edit");
        Scanner scanner = new Scanner(System.in);
        Long stockID = ConsoleUtil.readLong("Stock");
        Stock searchedStock = stockService.findById(stockID);
        if (searchedStock == null) {
            System.out.println("Stock not found");
        } else {
            try {
                System.out.println("Enter new quantity: ");
                String quantityS = scanner.nextLine();
                Integer quantity = Integer.valueOf(quantityS);
                searchedStock.setQuantity(quantity);
                stockService.updateQuantity(searchedStock);
                System.out.println("Car updated");
            } catch (InputMismatchException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayStockDetails() {
        System.out.println("Choose stock by ID: ");
        Long id = ConsoleUtil.readLong("Stock");
        Stock searchedStock = stockService.findById(id);
        if (searchedStock == null) {
            System.out.println("Stock not found");
        } else {
            System.out.println("Stock details: ");
            System.out.print("Stock ID: " + searchedStock.getId());
            System.out.print(" , Quantity: " + searchedStock.getQuantity());
            System.out.print (" , Location: " + searchedStock.getLocation());
            System.out.println(" , Car: " + searchedStock.getCar());//trebuie regandit sa furnizeze id-ul Car
        }

    }
    /*Main Menu
1. Cars
2. Clients
3. Orders
4. Stocks
0. Exit
Your option is:
4
Stocks menu
Creating a statement...
Creating a statement...
1. View all stocks
2. View stock details
3. Edit stock
4. Add new stock
5. Delete stock
0. Exit
Your option is:
1
Stocks list:
Stock ID: 1 , Quantity: 33 , Location: Iasi , Car: 1

Stock ID: 2 , Quantity: 234 , Location: Oradea , Car: 2

1. View all stocks
2. View stock details
3. Edit stock
4. Add new stock
5. Delete stock
0. Exit
Your option is:

STRUCTURA TABELA stock -- leagatura one-to-one cu tabela car
id, quantity, location, car_id

DDL
CREATE TABLE `stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quantity` int(11) DEFAULT NULL,
  `location` varchar(45) DEFAULT NULL,
  `car_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `car_id_UNIQUE` (`car_id`),
  CONSTRAINT `id` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
    */
}
