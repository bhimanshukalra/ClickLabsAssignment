package com.bhimanshukalra;

import java.util.ArrayList;

public class Customer {
    private int ID;
    private String name;
    private ArrayList<Car> purchasedCars = new ArrayList<>();

    public Customer(int ID, String name, Car car) {
        this.ID = ID;
        this.name = name;
        purchasedCars.add(car);
    }

    public static void printDetails(Customer customer) {
        System.out.print(customer.ID + " " + customer.name + " ");
        printAllCars(customer.purchasedCars);
    }

    public static void printAllCars(ArrayList<Car> purchasedCars) {
        for (int index = 0; index < purchasedCars.size(); index++) {
            if(index==purchasedCars.size()-1)
                System.out.println(purchasedCars.get(index).getModel());
            else
                System.out.print(purchasedCars.get(index).getModel()+", ");
        }
    }

    public static void addNewCarToExistingCustomer(Customer customer, Car car) {
        customer.purchasedCars.add(car);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Car> getPurchasedCars() {
        return purchasedCars;
    }
}
