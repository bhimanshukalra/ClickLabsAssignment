package com.bhimanshukalra;

import java.util.*;

import static com.bhimanshukalra.Customer.printDetails;

public class Admin {

    //Helper function to get valid integer input
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

    //Helper function to get non-integer string input
    private static String getStringInput(Scanner scanner) {
        String input = scanner.nextLine();
        if(containsInt(input)){
            System.out.println("Incorrect input");
            input = getStringInput(scanner);
        }
        return input;
    }

    //Displays menu and gets option selected
    private static int displayMenu(Scanner scanner) {
        System.out.print("\n0. Exit\n1. Add new Customer\n2. Add new car to an existing customer\n" +
                "3. List all customers with their cars sorted by name\n4. List individual customer based on ID\n" +
                "5. Generate prize for the customer\n6. Print whole list\n\nEnter choice: ");
        int optionOpted = getIntegerInput(scanner);
        if (optionOpted > 6) {
            System.out.println("Incorrect option");
            return displayMenu(scanner);
        }
        return optionOpted;
    }

    //Action according to option opted in main menu
    private static void mainMenu(HashMap<Integer, Customer> customersList, Scanner scanner) {
        int id=0;
        while (true) {
            int optionOpted = displayMenu(scanner);//Display menu and get option entered by user
            switch (optionOpted) {//Actions according to option chosen
                case 0:
                    return;
                case 1:
                    scanner.nextLine();
                    addNewCustomer(customersList, scanner, ++id);
                    break;
                case 2:
                    addNewCarToExistingCustomer(customersList, scanner, id);
                    break;
                case 3:
                    printSortedCustomerList(customersList);
                    break;
                case 4:
                    printCustomerAccordingToID(customersList, scanner, id);
                    break;
                case 5:
                    prizeGiveAway(customersList, scanner, id);
                    break;
                case 6:
                    printCustomerList(customersList);
                    break;
            }
        }
    }

    private static String capitalizeFirstLetter(String string){
        String firstChar = String.valueOf(string.charAt(0)).toUpperCase();
        return firstChar + string.substring(1);
    }

    //Add new car of customer
    private static Car addNewCar(Scanner scanner) {
        Car car;
        System.out.print("Enter Company name(Hyundai, Maruti, Toyota): ");
        String companyName = scanner.nextLine();
        companyName = companyName.toLowerCase();
        if(containsInt(companyName) || ( (!companyName.equals("toyota")) && (!companyName.equals("hyundai")) && (!companyName.equals("maruti"))) ) {
            System.out.println("Incorrect input");
            return addNewCar(scanner);
        }
        companyName = capitalizeFirstLetter(companyName);
        System.out.print("Enter car's ID: ");
        int ID = getIntegerInput(scanner);
        scanner.nextLine();
        System.out.print("Enter car's model: ");
        String model = scanner.nextLine();
        System.out.print("Enter car's price: ");
        int price = getIntegerInput(scanner);
        model=companyName+" "+model;

        switch (companyName){
            case "Toyota":
                car = new Toyota(ID, model, price);
                break;
            case "Maruti":
                car = new Maruti(ID, model, price);
                break;
            case "Hyundai":
                car = new Hyundai(ID, model, price);
                break;
            default:
                System.out.println("Incorrect input.");
                car = addNewCar(scanner);
        }
        return car;
    }

    //Checks if a string contains an integer
    private static boolean containsInt(String name){
        for(int i=0;i<name.length();i++){
            if(name.charAt(i)>='0' && name.charAt(i)<='9'){
                return true;
            }
        }
        return false;
    }

    //Add new customer to data
    private static void addNewCustomer(HashMap<Integer, Customer> customersList, Scanner scanner, int id) {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        if(containsInt(name)){
            System.out.println("Incorrect input");
            addNewCustomer(customersList, scanner, id);
            return;
        }
        Car car = addNewCar(scanner);
        Customer customer = new Customer(id, name, car);
        printDetails(customer);
        customersList.put(id, customer);
    }

    //Add new car of pre existing customer to data
    private static void addNewCarToExistingCustomer(HashMap<Integer, Customer> customersList, Scanner scanner, int maxID) {
        System.out.print("Enter ID: ");
        int ID = getIntegerInput(scanner);
        if(ID>maxID){
            System.out.println("Incorrect ID");
            addNewCarToExistingCustomer(customersList, scanner, maxID);
            return;
        }
        Customer customer = customersList.get(ID);
        scanner.nextLine();
        Car car = addNewCar(scanner);
        Customer.addNewCarToExistingCustomer(customer, car);
        printDetails(customer);
    }

    //Print customers and cars data
    private static void printCustomerAndCars(String customer, ArrayList<Car> purchasedCars) {
        System.out.print(customer + " -> ");
        Customer.printAllCars(purchasedCars);
    }

    //Print customer name and cars (sorted according to customer name)
    private static void printSortedCustomerList(HashMap<Integer, Customer> customersList) {
        if(customersList.keySet().size()==0){
            System.out.println("Empty List");
            return;
        }
        TreeMap<String, ArrayList<Car>> sortedCustomersList = new TreeMap<>();
        for (int ID : customersList.keySet()) {
            Customer customer = customersList.get(ID);
            sortedCustomersList.put(customer.getName(), customer.getPurchasedCars());
        }
        System.out.println("---------CUSTOMER LIST---------");
        for (String customer : sortedCustomersList.keySet()) {
            ArrayList<Car> purchasedCars = sortedCustomersList.get(customer);
            printCustomerAndCars(customer, purchasedCars);
        }
        System.out.println("-------------------------------");
    }

    private static void printCustomerList(HashMap<Integer, Customer> customersList) {
        if(customersList.keySet().size()==0){
            System.out.println("Empty List");
            return;
        }
        System.out.println("---------CUSTOMER LIST---------");
        for(int ID : customersList.keySet()){
            Customer customer = customersList.get(ID);
            String name = customer.getName();
            ArrayList<Car> carArrayList = customer.getPurchasedCars();
            System.out.print(ID+" "+name+" -> ");
            for(int i=0;i<carArrayList.size();i++){
                Car car = carArrayList.get(i);
                String model = car.getModel();
                int price = car.getPrice();
                int resaleValue = car.getResaleValue();
                if(i==carArrayList.size()-1)
                    System.out.print(model+" ("+price+", "+resaleValue+")");
                else
                    System.out.print(model+" ("+price+", "+resaleValue+"), ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------");
    }

    //Print individual customer according to ID
    private static void printCustomerAccordingToID(HashMap<Integer, Customer> customersList, Scanner scanner, int maxID) {
        if(customersList.size()==0){
            System.out.println("No customers");
            return;
        }
        System.out.print("Enter customer ID: ");
        int ID = getIntegerInput(scanner);
        if(ID>maxID){
            System.out.println("Incorrect ID (ID should be less than "+maxID+").");
            printCustomerAccordingToID(customersList, scanner, maxID);
            return;
        }
        Customer customer = customersList.get(ID);
        System.out.println(ID + " " + customer.getName());
    }

    //Generates 6 random customer ID for prize
    private static Set<Integer> getPriceGiveAwayId(ArrayList<Integer> IDArrayList) {
        int maxIndex = IDArrayList.size() - 1;
        Set<Integer> randomPrizeID = new HashSet<>(6);//6 is the size of random IDs generated by computer
        Random random = new Random();
        while (true) {
            int randomIndex = random.nextInt(maxIndex + 1);
            randomPrizeID.add(IDArrayList.get(randomIndex));
            if (randomPrizeID.size() == 6)
                break;
        }
        return randomPrizeID;
    }

    //Compare random ID list(generated by computer) and the ID given by admin
    private static void prizeGiveAway(HashMap<Integer, Customer> customersList, Scanner scanner, int maxID) {
        ArrayList<Integer> IDArrayList = new ArrayList<>(customersList.keySet());
        if (IDArrayList.size() < 6) {
            System.out.println("Customer IDs should be greater than 5");
            return;
        }
        Set<Integer> randomPrizeID = getPriceGiveAwayId(IDArrayList);
//        System.out.println("randomPrizeID: "+randomPrizeID);
        System.out.print("Enter three customer IDs: ");
        ArrayList<Integer> customerIDList = new ArrayList<>(3);//3 numbers will be entered by user.
        for (int i = 0; i < 3; i++) {
            int ID = getIntegerInput(scanner);
            if(ID>maxID){
                System.out.println("Incorrect ID");
                prizeGiveAway(customersList, scanner, maxID);
                return;
            }
            customerIDList.add(ID);
        }
        printCustomersEligibleForPrize(randomPrizeID, customerIDList);
        System.out.println();
    }

    //Print all the prize winners
    private static void printCustomersEligibleForPrize(Set<Integer> randomPrizeID, ArrayList<Integer> customerIDList) {
        boolean prizeWon = false;
        System.out.print("Customers eligible for prize: ");
        for (int i = 0; i < 3; i++)
            if (randomPrizeID.contains(customerIDList.get(i))) {
                prizeWon = true;
                System.out.print(customerIDList.get(i) + " ");
            }
        if (prizeWon)
            return;
        System.out.println("None");
    }

    public static void main(String[] args) {
        HashMap<Integer, Customer> customersList = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        mainMenu(customersList, scanner);//Main function having all the functionality
    }
}
