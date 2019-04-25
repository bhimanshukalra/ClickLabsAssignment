package com.bhimanshukalra;

import java.util.ArrayList;

public class Customer {
    private int Id;
    private String name;
    private ArrayList<Car> purchasedCars = new ArrayList<>();

    public Customer(int Id, String name, Car car) {
        this.Id = Id;
        this.name = name;
        purchasedCars.add(car);
    }

    //Print Id, name, and all the cars of each customer
    public static void printDetails(Customer customer) {
        System.out.print(customer.Id + " " + customer.name + " ");
        printAllCars(customer.purchasedCars);
    }

    //Print all cars of from arrayList passed
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
