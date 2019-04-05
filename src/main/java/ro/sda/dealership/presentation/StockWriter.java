package ro.sda.dealership.presentation;

import ro.sda.dealership.model.Stock;

import java.util.List;

public class StockWriter implements ConsoleWriter<Stock> {

    public void write(Stock stock) {
        System.out.print("Stock ID: " + stock.getId());
        System.out.print(" , Quantity: " + stock.getQuantity());
        System.out.print(" , Location: " + stock.getLocation());
        System.out.print(" , Car: " + stock.getCar().getId());
        System.out.println();
        /*System.out.print("Card ID: " + stock.getCar().getId());
        System.out.println("Car model: " + stock.getCar().getCarModel());
        System.out.println("Car color: " + stock.getCar().getCarColor());
        System.out.println("Car quantity: " + stock.getQuantity());
        System.out.println("Car location: " + stock.getLocation());
        System.out.println("Car price: " + stock.getCar().getCarPrice());*/
    }

    void writeAll(List<Stock> stocks) {
        if (stocks.size() == 0) {
            System.out.println("No cars available");
        } else {
            System.out.println("Stocks list: ");
            for (Stock stock : stocks) {
                writeSummary(stock);
            }
        }
    }

    private void writeSummary(Stock stock) {
        System.out.print("Stock ID: " + stock.getId());
        System.out.print(" , Quantity: " + stock.getQuantity());
        System.out.print(" , Location: " + stock.getLocation());
        System.out.println(" , Car: " + stock.getCar().getId());
        System.out.println();
        //System.out.print(", Card ID: " + stock.getCar().getId());
        /*System.out.print(", Car model: " + stock.getCar().getCarModel());
        System.out.print(", Car color: " + stock.getCar().getCarColor());
        System.out.print(", Car quantity: " + stock.getQuantity());
        System.out.print(", Car location: " + stock.getLocation());
        System.out.println(", Car price: " + stock.getCar().getCarPrice());*/
    }

}
