package com.bhimanshukalra;

import java.util.*;

import static com.bhimanshukalra.Customer.printDetails;

public class Admin {

    //Helper function to get valid integer input
    private static int getIntegerInput(String printStr, Scanner scanner) {
        System.out.print(printStr);
        String input = scanner.nextLine();
        int num;
        try {
            num = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Incorrect input.");
            num = getIntegerInput(printStr, scanner);
        }
        return num;
    }

    //Helper function to get non-integer string input
    private static String getStringInput(String printStr, Scanner scanner) {
        System.out.print(printStr);
        String input = scanner.nextLine();
        if(containsInt(input)){
            System.out.println("Incorrect input");
            input = getStringInput(printStr, scanner);
        }
        return input;
    }

    //Displays menu and gets option selected
    private static int displayMenu(Scanner scanner) {
        System.out.print("\n0. Exit\n1. Add new Customer\n2. Add new car to an existing customer\n" +
                "3. List all customers with their cars sorted by name\n4. List individual customer based on Id\n" +
                "5. Generate prize for the customer\n6. Print whole list\n\n");
        int optionOpted = getIntegerInput("Enter choice: ",scanner);
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
                    addNewCustomer(customersList, scanner, ++id);
                    break;
                case 2:
                    addNewCarToExistingCustomer(customersList, scanner, id);
                    break;
                case 3:
                    printSortedCustomerList(customersList);//Print customer list sorted by customer name
                    break;
                case 4:
                    printCustomerAccordingToId(customersList, scanner, id);//Fetch customer having the Id entered by user
                    break;
                case 5:
                    prizeGiveAway(customersList, scanner, id);
                    break;
                case 6:
                    printDetailedCustomerList(customersList);
                    break;
            }
        }
    }

    //This function capitalizes first letter of the string
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
        //Validating company name entered by user
        if(containsInt(companyName) || ( (!companyName.equals("toyota")) && (!companyName.equals("hyundai")) && (!companyName.equals("maruti"))) ) {
            System.out.println("Incorrect input");
            return addNewCar(scanner);
        }
        companyName = capitalizeFirstLetter(companyName);
        int Id = getIntegerInput("Enter car's Id: ", scanner);
        System.out.print("Enter car's model (e.g. Verna, i10, etc): ");
        String model = scanner.nextLine();
        int price = getIntegerInput("Enter car's price: ", scanner);
        if(model.charAt(0)>='a' && model.charAt(0)<='z')
            model = capitalizeFirstLetter(model);

        switch (companyName){
            case "Toyota":
                car = new Toyota(Id, companyName+" "+model, price);
                break;
            case "Maruti":
                car = new Maruti(Id, companyName+" "+model, price);
                break;
            case "Hyundai":
                car = new Hyundai(Id, companyName+" "+model, price);
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
        String name = getStringInput("Enter customer name: ", scanner);
        name = capitalizeFirstLetter(name);
        if(containsInt(name)){//validate name
            System.out.println("Incorrect input");
            addNewCustomer(customersList, scanner, id);
            return;
        }
        Car car = addNewCar(scanner);
        Customer customer = new Customer(id, name, car);
        printDetails(customer);
        customersList.put(id, customer);
    }

    //Add new car of pre existing customer
    private static void addNewCarToExistingCustomer(HashMap<Integer, Customer> customersList, Scanner scanner, int maxId) {
        if(maxId==0) {
            System.out.println("No customers in db.");
            return;
        }
        int id = getIntegerInput("Enter Id: ", scanner);
        if(id<=0 || id>maxId){
            System.out.println("Incorrect Id (Id should be less than "+(maxId+1)+").");
            addNewCarToExistingCustomer(customersList, scanner, maxId);
            return;
        }
        Customer customer = customersList.get(id);
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
        for (int Id : customersList.keySet()) {
            Customer customer = customersList.get(Id);
            sortedCustomersList.put(customer.getName(), customer.getPurchasedCars());
        }
        System.out.println("---------CUSTOMER LIST---------");
        for (String customer : sortedCustomersList.keySet()) {
            ArrayList<Car> purchasedCars = sortedCustomersList.get(customer);
            printCustomerAndCars(customer, purchasedCars);
        }
        System.out.println("-------------------------------");
    }

    //List of all customer with there Id, name and cars(price and resale price)
    private static void printDetailedCustomerList(HashMap<Integer, Customer> customersList) {
        if(customersList.keySet().size()==0){
            System.out.println("Empty List");
            return;
        }
        System.out.println("---------CUSTOMER LIST---------");
        for(int Id : customersList.keySet()){
            Customer customer = customersList.get(Id);
            String name = customer.getName();
            ArrayList<Car> carArrayList = customer.getPurchasedCars();
            System.out.print(Id+" "+name+" -> ");
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

    //Print individual customer according to Id
    private static void printCustomerAccordingToId(HashMap<Integer, Customer> customersList, Scanner scanner, int maxId) {
        if(customersList.size()==0){
            System.out.println("No customers");
            return;
        }
        int Id = getIntegerInput("Enter customer Id: ", scanner);
        if(Id>maxId){
            System.out.println("Incorrect Id (Id should be less than "+maxId+").");
            printCustomerAccordingToId(customersList, scanner, maxId);
            return;
        }
        Customer customer = customersList.get(Id);
        System.out.println(Id + " " + customer.getName());
    }

    //Generates 6 random customer Id for prize
    private static Set<Integer> getPriceGiveAwayId(ArrayList<Integer> IdArrayList) {
        int maxIndex = IdArrayList.size() - 1;
        Set<Integer> randomPrizeId = new HashSet<>(6);//6 is the size of random Ids generated by computer
        Random random = new Random();
        while (true) {
            int randomIndex = random.nextInt(maxIndex + 1);
            randomPrizeId.add(IdArrayList.get(randomIndex));
            if (randomPrizeId.size() == 6)
                break;
        }
        return randomPrizeId;
    }

    //Compare random Id list(generated by computer) and the Id given by admin
    private static void prizeGiveAway(HashMap<Integer, Customer> customersList, Scanner scanner, int maxId) {
        ArrayList<Integer> IdArrayList = new ArrayList<>(customersList.keySet());
        if (IdArrayList.size() < 6) {
            System.out.println("Customer Ids should be greater than 5");
            return;
        }
        Set<Integer> randomPrizeIdList = getPriceGiveAwayId(IdArrayList);
        System.out.println("Enter three customer Ids: ");
        ArrayList<Integer> customerIdList = new ArrayList<>(3);//3 numbers will be entered by user.
        for (int i = 0; i < 3; i++) {
            int Id = getIntegerInput("Id "+i+": ", scanner);
            if(Id>maxId || Id<1){
                System.out.println("Incorrect Id");
                prizeGiveAway(customersList, scanner, maxId);
                return;
            }
            customerIdList.add(Id);
        }
        printCustomersEligibleForPrize(randomPrizeIdList, customerIdList);
        System.out.println();
    }

    //Print all the prize winners
    private static void printCustomersEligibleForPrize(Set<Integer> randomPrizeId, ArrayList<Integer> customerIdList) {
        boolean prizeWon = false;
        System.out.print("Customers eligible for prize: ");
        for (int i = 0; i < 3; i++)
            if (randomPrizeId.contains(customerIdList.get(i))) {
                prizeWon = true;
                System.out.print(customerIdList.get(i) + " ");
            }
        if (prizeWon)
            return;
        System.out.println("None");
    }

    public static void main(String[] args) {
        HashMap<Integer, Customer> customersList = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        mainMenu(customersList, scanner);//The root function having all the functionality
    }
}
