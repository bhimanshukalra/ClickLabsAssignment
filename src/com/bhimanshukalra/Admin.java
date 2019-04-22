package com.bhimanshukalra;
import java.util.*;
import static com.bhimanshukalra.Customer.printDetails;

public class Admin {

    private static HashMap<Integer, Customer> customersList = new HashMap<>();
    private static TreeMap<String, ArrayList<Car>> sortedCustomersList = new TreeMap<>();

    //Helper function to check if integer input is valid
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

    //Displays menu and gets option selected
    private static int displayMenu(Scanner scanner) {
        System.out.print("\n0. Exit\n1. Add new Customer\n2. Add new car to an existing customer\n" +
                "3. List all customers with their cars sorted by name\n4. List individual customer based on ID\n" +
                "5. Generate prize for the customer\n\nEnter choice: ");
        int optionOpted = getIntegerInput(scanner);
        if (optionOpted > 5) {
            System.out.println("Incorrect option");
            displayMenu(scanner);
        }
        return optionOpted;
    }

    //Action according to option opted in main menu
    private static void mainMenu(Scanner scanner) {
        //int id=0;
        int id = temp();
        while (true) {
            int optionOpted = displayMenu(scanner);
            switch (optionOpted) {
                case 0:
                    return;
                case 1:
                    addNewCustomer(scanner, ++id);
                    break;
                case 2:
                    addNewCarToExistingCustomer(scanner);
                    break;
                case 3:
                    printSortedCustomerList();
                    break;
                case 4:
                    printCustomerAccordingToID(scanner);
                    break;
                case 5:
                    prizeGiveAway(scanner);
                    break;
            }
        }
    }

    //Add new car of customer
    private static Car addNewCar(Scanner scanner) {
        Car car;
        System.out.print("Enter car's ID, model and price respectively: ");
        int ID = getIntegerInput(scanner);
        scanner.nextLine();
        String model = scanner.nextLine();
        int price = getIntegerInput(scanner);
        String companyName = model.substring(0, model.indexOf(' '));

        if (companyName.equals("Toyota"))
            car = new Toyota(ID, model, price);
        else if (companyName.equals("Maruti"))
            car = new Maruti(ID, model, price);
        else if (companyName.equals("Hyundai"))
            car = new Hyundai(ID, model, price);
        else {
            System.out.println("Incorrect input.");
            car = addNewCar(scanner);
        }
        return car;
    }

    //Add new customer to data
    private static void addNewCustomer(Scanner scanner, int id) {
        scanner.nextLine();
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        Car car = addNewCar(scanner);
        Customer customer = new Customer(id, name, car);
        printDetails(customer);
        System.out.println();
        customersList.put(id, customer);
        sortedCustomersList.put(customer.getName(), customer.getPurchasedCars());
    }

    //Add new car of pre existing customer to data
    private static void addNewCarToExistingCustomer(Scanner scanner) {
        System.out.print("Enter ID: ");
        int ID = getIntegerInput(scanner);
        Customer customer = customersList.get(ID);
        scanner.nextLine();
        Car car = addNewCar(scanner);
        Customer.addNewCarToExistingCustomer(customer, car);
    }

    //Print customers and cars data
    private static void printCustomerAndCars(String customer, ArrayList<Car> purchasedCars) {
        System.out.print(customer + " ");
        Customer.printAllCars(purchasedCars);
    }

    //Print customer name and cars (sorted according to customer name)
    private static void printSortedCustomerList() {
        System.out.println("---------CUSTOMER LIST---------");
        for (String customer : sortedCustomersList.keySet()) {
            ArrayList<Car> purchasedCars = sortedCustomersList.get(customer);
            printCustomerAndCars(customer, purchasedCars);
        }
        System.out.println("-------------------------------");
    }

    //Print individual customer according to ID
    private static void printCustomerAccordingToID(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        int ID = getIntegerInput(scanner);
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
        //System.out.println("randomPrizeID: "+randomPrizeID);
        return randomPrizeID;
    }

    //Compare random ID list(generated by computer) and the ID given by admin
    private static void prizeGiveAway(Scanner scanner) {
        ArrayList<Integer> IDArrayList = new ArrayList<>(customersList.keySet());
        //System.out.println("IDArrayList: "+IDArrayList);
        if(IDArrayList.size()<6) {
            System.out.println("Customer IDs should be greater than 5");
            return;
        }
        Set<Integer> randomPrizeID = getPriceGiveAwayId(IDArrayList);
        System.out.print("Enter three customer IDs: ");
        ArrayList<Integer> customerIDList = new ArrayList<>(3);//3 numbers will be entered by user.
        for (int i = 0; i < 3; i++)
            customerIDList.add(getIntegerInput(scanner));
        printCustomersEligibleForPrize(randomPrizeID, customerIDList);
        System.out.println();
    }

    //Print all the prize winners
    private static void printCustomersEligibleForPrize(Set<Integer> randomPrizeID, ArrayList<Integer> customerIDList) {
        //System.out.println(randomPrizeID);
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

    //Temporary function to display the code functioning
    private static int temp() {
        int id = 0;
        Car car = new Toyota(++id, "one", 1111);
        Customer customer = new Customer(id, "One", car);
        customersList.put(1, customer);
        sortedCustomersList.put(customer.getName(), customer.getPurchasedCars());

        customer = new Customer(++id, "Two", car);
        customersList.put(2, customer);
        sortedCustomersList.put(customer.getName(), customer.getPurchasedCars());

        customer = new Customer(++id, "Three", car);
        customersList.put(3, customer);
        sortedCustomersList.put(customer.getName(), customer.getPurchasedCars());

        customer = new Customer(++id, "Four", car);
        customersList.put(4, customer);
        sortedCustomersList.put(customer.getName(), customer.getPurchasedCars());

        customer = new Customer(++id, "Five", car);
        customersList.put(5, customer);
        sortedCustomersList.put(customer.getName(), customer.getPurchasedCars());

        customer = new Customer(++id, "Six", car);
        customersList.put(6, customer);
        sortedCustomersList.put(customer.getName(), customer.getPurchasedCars());

        customer = new Customer(++id, "Seven", car);
        customersList.put(7, customer);
        sortedCustomersList.put(customer.getName(), customer.getPurchasedCars());

        customer = new Customer(++id, "Eight", car);
        customersList.put(8, customer);
        sortedCustomersList.put(customer.getName(), customer.getPurchasedCars());

        customer = new Customer(++id, "Nine", car);
        customersList.put(9, customer);
        sortedCustomersList.put(customer.getName(), customer.getPurchasedCars());

        return id;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        mainMenu(scanner);
    }
}
