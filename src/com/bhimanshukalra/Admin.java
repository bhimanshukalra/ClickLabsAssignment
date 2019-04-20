package com.bhimanshukalra;

import java.util.*;

import static com.bhimanshukalra.Customer.printDetails;

public class Admin {

    private static HashMap<Integer, Customer> customersList;
//    private static HashMap<Integer, Car> carsList;

    private static void priceGiveAwayId(ArrayList<Integer> arrayList) {
//        int min=0, max=10;
        int maxIndex = arrayList.size() - 1;
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            int randomIndex = random.nextInt(maxIndex + 1);
            System.out.println(arrayList.get(randomIndex));
        }
    }

    private static int displayMenu(Scanner scanner) {
        System.out.print("0. Exit\n1. Add new Customer\n2. Add new car to an existing customer\n3. List all customers\n\nEnter choice: ");
        int optionOpted = getIntegerInput(scanner);
        if (optionOpted > 5) {
            System.out.println("Incorrect option");
            displayMenu(scanner);
        }
        return optionOpted;
    }

    private static int getIntegerInput(Scanner scanner) {
        String input = scanner.next();
        int num;
        try {
            num = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Incorrect input.");
            num = getIntegerInput(scanner);
        }
        return num;
    }

    private static Car addNewCar(Scanner scanner){
        Car car;
        System.out.print("Enter car's ID, model and price respectively: ");
        int ID = getIntegerInput(scanner);
        scanner.nextLine();
        String model = scanner.nextLine();
        int price = getIntegerInput(scanner);
        String companyName = model.substring(0, model.indexOf(' '));

        if(companyName.equals("Toyota"))
            car = new Toyota(ID, model, price);
        else if(companyName.equals("Maruti"))
            car = new Maruti(ID, model, price);
        else if(companyName.equals("Hyundai"))
            car = new Hyundai(ID, model, price);
        else {
            System.out.println("Incorrect input.");
            car = addNewCar(scanner);
        }
//        System.out.println("Enter car's ID, model, price");
//        Toyota car = new Toyota(ID, model, price, resaleValue);
//        carsList.put(ID, car);
        return car;
    }

    private static void addNewCustomer(Scanner scanner, int id){
//        int ID = getIntegerInput(scanner);
//        String purchasedCar = scanner.nextLine();
//        Customer customer = new Customer(ID, name, purchasedCar);
        scanner.nextLine();
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        Car car = addNewCar(scanner);
        Customer customer = new Customer(id, name, car);
        printDetails(customer);
        System.out.println();
        customersList.put(id, customer);
//        customer = new Customer(2, "Kalra", "I40");
//        System.out.println("Customer details: "+customer);
//        customersList.put(2, customer);
    }

    private static void init(){
        customersList=new HashMap<>();
//        carsList=new HashMap<>();
    }

    private static void printSortedCustomerList(){
        System.out.println("---------CUSTOMER LIST---------");
        for(int key : customersList.keySet()) {
            Customer customer=customersList.get(key);
            String name = customer.getName();
            Customer.printDetails(customer);
        }
        System.out.println("-------------------------------");
//        System.out.println(customersList);
    }

    private static void addNewCarToExistingCustomer(Scanner scanner){
//        System.out.println("Cus start");
        System.out.println("Enter ID: ");
        int ID = getIntegerInput(scanner);
        Customer customer = customersList.get(ID);
        scanner.nextLine();
        Car car = addNewCar(scanner);
        Customer.addNewCarToExistingCustomer(customer, car);
//        Customer.addNewCarToExistingCustomer(customer, carName);
//        System.out.println("Cus stop");
    }

    private static void printCustomerAccordingToID(Scanner scanner){
        int ID = getIntegerInput(scanner);
        Customer customer = customersList.get(ID);
        String carName = scanner.nextLine();
//        Customer.addNewCarToExistingCustomer(customer, carName);
    }

    private static void mainMenu(Scanner scanner){
        int id=0;
        while (true){
            int optionOpted = displayMenu(scanner);
            switch (optionOpted){
                case 0:
                    return;
                case 1:
                    addNewCustomer(scanner, ++id);
                    break;
                case 2:
                    addNewCarToExistingCustomer(scanner);
                    break;
                case 3:
                    printCustomerList();
                    break;
                case 4:
                    printCustomerAccordingToID(scanner);
                    break;
                case 5:

                    break;
            }
        }
    }

    public static void main(String[] args) {
        init();
        Scanner scanner = new Scanner(System.in);
        mainMenu(scanner);

//        int[] arr={};
//        ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(2,5,1,7,8,9,10,12,15,13));
//        priceGiveAwayId(arrayList);
    }
}
