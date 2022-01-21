package com.Nikhil;
/*
    Name :- NIKHIL SARASWAT
    Roll No.:- 20CS10039
*/

import java.util.*;

// This is the base class of all the entities which is containing the entity name and entity id
class Entity {
    Integer id;
    String name;
}

// Below all the classes have been inherited from the Entity (base) Class
class Product extends Entity {
    Integer Manufacturer;

    //constructor
    Product(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    void setManufacturer(Integer Id) {
        this.Manufacturer = Id;
    }
}

class Manufacturer extends Entity {
    ArrayList<Integer> Products = new ArrayList<>();

    //constructor
    Manufacturer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    void addProduct(Integer productID) {
        if (this.Products.contains(productID)) {
            return;
        }
        this.Products.add(productID);
    }

    void DeleteProduct(Integer productID) {
        this.Products.remove(productID);
    }
}

class DeliveryAgent extends Entity {
    int ZipCode;
    int DeleveryNumber = 0;

    //constructor
    DeliveryAgent(int id, String name, int zip) {
        this.id = id;
        this.ZipCode = zip;
        this.name = name;
    }

    void addProduct() {
        this.DeleveryNumber++;
    }
}

class Shop extends Entity {
    int ZipCode;
    HashMap<Integer, Integer> Product_Copies = new HashMap<>();

    //constructor
    Shop(int id, String name, int Zip) {
        this.id = id;
        this.ZipCode = Zip;
        this.name = name;
    }

    void AddorUpdateCopies(Integer productID, Integer newcopy) {
        if (Product_Copies.containsKey(productID)) {
            int oldcopy = Product_Copies.get(productID);
            Product_Copies.replace(productID, oldcopy, (newcopy + oldcopy));
        } else {
            Product_Copies.put(productID, newcopy);
        }
    }

    void DeleteProduct(Integer productID) {
        if (Product_Copies.containsKey(productID)) {
            this.Product_Copies.remove(productID);
        }
    }
}

class Customer extends Entity {
    int ZipCode;
    ArrayList<Integer> ProductsList = new ArrayList<>();
    HashMap<Integer, Integer> Product_Count = new HashMap<>();

    //constructor
    Customer(int id, String name, int Zip) {
        this.id = id;
        this.ZipCode = Zip;
        this.name = name;
    }

    void addProduct(Integer productID, Integer count) {
        if (this.ProductsList.contains(productID)) {
            Integer oldcount = Product_Count.get(productID);
            if (this.Product_Count.containsKey(productID))
                this.Product_Count.replace(productID, oldcount, (oldcount + count));
            return;
        }
        this.ProductsList.add(productID);
        this.Product_Count.put(productID, count);
    }
}

class Order {
    Product product;
    Customer customer;
    Integer copies;

    //constructor
    Order(Product Prod, Customer cm, int copy) {
        this.product = Prod;
        this.customer = cm;
        this.copies = copy;
    }
}

// creates the comparator for comparing Number of Deliveries
class sorterDeliveryAgents implements Comparator<DeliveryAgent> { //sorting is done in increasing order of Number of deliveries, if number of deliveries are same then sorting is done in increasing order of IDs
    // override the compare() method
    public int compare(DeliveryAgent l1, DeliveryAgent l2) {
        if (l1.DeleveryNumber == l2.DeleveryNumber) {
            if (l1.id > l2.id) return 1;
            else return -1;
        } else if (l1.DeleveryNumber > l2.DeleveryNumber) return 1;
        else return -1;
    }
}

/* This is the Master Interface which controls all the functions of the Online Medical Shop */
class MasterInterface {
    // Below all the Data Structures are for storing data all the Entities
    ArrayList<Integer> ExistingIDs = new ArrayList<>();
    HashMap<Integer, String> ProductIdNameMap = new HashMap<>();
    ArrayList<Manufacturer> ManufacturerList = new ArrayList<>();
    ArrayList<Product> ProductList = new ArrayList<>();
    ArrayList<Shop> ShopList = new ArrayList<>();
    ArrayList<Customer> CustomerList = new ArrayList<>();
    ArrayList<Order> OrderList = new ArrayList<>();
    ArrayList<DeliveryAgent> DeliveryAgentList = new ArrayList<>();

    /*Below all the functions are for controlling all the actions*/

    //This function is for adding products to the Product ArrayList
    void AddProductToProductList(Product product) {
        for (Product pro : this.ProductList) {
            if (pro.id.equals(product.id)) return;
        }
        this.ProductList.add(product);
        this.ProductIdNameMap.put(product.id, product.name);
    }

    //This function is for adding ID to the Existing ID ArrayList to maintain uniqueness of IDs
    void AddIDtoExistingIDset(Integer ID) {
        if (this.ExistingIDs.contains(ID)) return;
        this.ExistingIDs.add(ID);
    }

    //This function is to check whether the ID already exists in the records or not
    boolean checkID_invalidity(Integer ID) {
        return this.ExistingIDs.contains(ID);
    }

    //This function is to check whether the given Product ID already exists in the records or not
    boolean checkProductID(Integer ID) {
        for (Product product : this.ProductList) {
            if (product.id.equals(ID)) return true;
        }
        return false;
    }

    //This function is to check whether the given Manufacturer ID already exists in the records or not
    boolean checkManufacturerID(Integer ID) {
        for (Manufacturer manufacturer : this.ManufacturerList) {
            if (manufacturer.id.equals(ID)) return true;
        }
        return false;
    }

    //This function is to check whether the given Customer ID already exists in the records or not
    boolean checkCustomerID(Integer ID) {
        for (Customer customer : this.CustomerList) {
            if (customer.id.equals(ID)) return true;
        }
        return false;
    }

    //This function is to check whether the given Shop ID already exists in the records or not
    boolean checkShopID(Integer ID) {
        for (Shop shop : this.ShopList) {
            if (shop.id.equals(ID)) return true;
        }
        return false;
    }

    //This function is to check whether the given delivery agent ID already exists in the records or not
    boolean checkdeliveryAgentID(Integer ID) {
        for (DeliveryAgent deliveryAgent : this.DeliveryAgentList) {
            if (deliveryAgent.id.equals(ID)) return true;
        }
        return false;
    }

    //This function is for adding products to the manufacturers
    boolean AddProdToManufacturer(Integer productID, Integer manufacturerID) {
        if (this.checkProductID(productID) && this.checkManufacturerID(manufacturerID)) {
            for (int i = 0; i < this.ManufacturerList.size(); i++) {
                if (this.ManufacturerList.get(i).id.equals(manufacturerID)) {
                    if (this.ManufacturerList.get(i).Products.contains(productID)) {
                        return true;
                    }
                    this.ManufacturerList.get(i).addProduct(productID);
                }
            }
            for (int i = 0; i < this.ProductList.size(); i++) {
                if (this.ProductList.get(i).id.equals(productID)) {
                    this.ProductList.get(i).setManufacturer(manufacturerID);
                }
            }
            for (int i = 0; i < this.ManufacturerList.size(); i++) {
                if (!(this.ManufacturerList.get(i).id.equals(manufacturerID))) {
                    for (int j = 0; j < this.ManufacturerList.get(i).Products.size(); j++) {
                        if (this.ManufacturerList.get(i).Products.get(j).equals(productID)) {
                            this.ManufacturerList.get(i).Products.remove(j);
                            break;
                        }
                    }
                }
            }
            return true;
        } else if (this.checkProductID(productID)) {
            System.out.println("Sorry, this manufacturer ID does not exist in our records!");
            return false;
        }
        System.out.println("Sorry, this Product ID does not exist in our records!");
        return false;
    }

    //This function is for Printing the list of products of a particular Manufacturer
    void ListProductsOfManufacturer(Integer manufacturerID) {
        if (this.checkManufacturerID(manufacturerID)) {
            for (int i = 0; i < this.ManufacturerList.size(); i++) {
                if (this.ManufacturerList.get(i).id.equals(manufacturerID)) {
                    if (this.ManufacturerList.get(i).Products.size() == 0) {
                        System.out.println("Sorry, There is no product in the list of this manufacturer ID!");
                        return;
                    }
                    System.out.println("The list of all products manufactured by this manufacturer is as following :-");
                    for (Integer productID : this.ManufacturerList.get(i).Products) {
                        System.out.println("ID : " + productID + ", Name : " + this.ProductIdNameMap.get(productID));
                    }
                }
            }
        } else {
            System.out.println("Sorry, this manufacturer does not exist in our records!");
        }
    }

    //This function is for Printing the list of products and their counts in a particular Shop
    void ListProductsOfInventories(Integer shopID) {
        if (this.checkShopID(shopID)) {
            for (int i = 0; i < this.ShopList.size(); i++) {
                if (this.ShopList.get(i).id.equals(shopID)) {
                    if (this.ShopList.get(i).Product_Copies.size() == 0) {
                        System.out.println("Sorry, There is no product in the list of this shop ID!");
                        return;
                    }
                    System.out.println("The list of all inventories of this shop is as following :-");
                    for (Map.Entry inventory : this.ShopList.get(i).Product_Copies.entrySet()) {
                        System.out.println("ID : " + inventory.getKey() + ", Name : " + this.ProductIdNameMap.get(inventory.getKey()) + ", Product Count : " + inventory.getValue());
                    }
                }
            }
        } else {
            System.out.println("Sorry, this shop does not exist in our records!");
        }
    }

    //This function is for adding products and counts to the shop
    boolean AddCopiesOfProductToShop(Integer shopID, Integer productID, Integer copy) {
        if (this.checkProductID(productID) && this.checkShopID(shopID)) {
            for (int i = 0; i < this.ShopList.size(); i++) {
                if (this.ShopList.get(i).id.equals(shopID)) {
                    this.ShopList.get(i).AddorUpdateCopies(productID, copy);
                }
            }
            return true;
        } else if (this.checkProductID(productID)) {
            System.out.println("Sorry, this shop does not exist in our records!");
            return false;
        }
        System.out.println("Sorry, this product does not exist in our records!");
        return false;
    }

    //This function is for adding products to the Customers
    boolean AddProdTocmr(Integer productID, Integer customerID, Integer count) {
        if (this.checkProductID(productID) && this.checkCustomerID(customerID)) {
            for (int i = 0; i < this.CustomerList.size(); i++) {
                if (this.CustomerList.get(i).id.equals(customerID)) {
                    this.CustomerList.get(i).addProduct(productID, count);
                }
            }
            return true;
        } else if (this.checkProductID(productID)) {
            System.out.println("Sorry, this customer does not exist in our records!");
            return false;
        }
        System.out.println("Sorry, this product does not exist in our records!");
        return false;
    }

    //This function is for Printing the list of products purchased by a particular Customer
    void ListPurchasesOfCustomer(Integer customerID) {
        if (this.checkCustomerID(customerID)) {
            for (int i = 0; i < this.CustomerList.size(); i++) {
                if (this.CustomerList.get(i).id.equals(customerID)) {
                    if (this.CustomerList.get(i).ProductsList.size() == 0) {
                        System.out.println("Sorry, There is no product in the list of this customer ID!");
                        return;
                    }
                    System.out.println("The list of all products purchased by this customer is as following :-");
                    for (Integer productID : this.CustomerList.get(i).ProductsList) {
                        System.out.println("ID : " + productID + ", Name : " + this.ProductIdNameMap.get(productID) + ", count : " + this.CustomerList.get(i).Product_Count.get(productID));
                    }
                }
            }
        } else {
            System.out.println("Sorry, this customer does not exist in our records!");
        }
    }

    //This function is for adding an order for a particular Customer
    boolean AddOrder(Customer customer, Product product, int copies) {
        if (checkCustomerID(customer.id) && checkProductID(product.id)) {
            Order newOrder = new Order(product, customer, copies);
            OrderList.add(newOrder);
            return true;
        } else if (this.checkProductID(product.id)) {
            System.out.println("Sorry, this customer does not exist in our records!");
            return false;
        }
        System.out.println("Sorry, this customer does not exist in our records!");
        return false;
    }

    //This function is for processing an order for a particular Customer
    Boolean processOrder(Order order) {
        this.DeliveryAgentList.sort(new sorterDeliveryAgents());
        for (Shop shop : this.ShopList) {
            if (shop.ZipCode == order.customer.ZipCode) {
                if (shop.Product_Copies.containsKey(order.product.id)) {
                    int oldcopy = shop.Product_Copies.get(order.product.id);
                    if (oldcopy >= order.copies) {
                        for (int i = 0; i < this.DeliveryAgentList.size(); i++) {
                            if (this.DeliveryAgentList.get(i).ZipCode == order.customer.ZipCode) {
                                if (oldcopy > order.copies)
                                    shop.Product_Copies.replace(order.product.id, oldcopy, oldcopy - order.copies);
                                else shop.Product_Copies.remove(order.product.id); // for equal number of copies
                                this.DeliveryAgentList.get(i).addProduct();
                                System.out.println("Your order has been dispatched from the shop '" + shop.name + "' and our delivery Agent '" + this.DeliveryAgentList.get(i).name + "' will deliver your order soon");
                                this.OrderList.remove(order);
                                this.AddProdTocmr(order.product.id, order.customer.id, order.copies);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    // This function is for some initial default (sample) data
    void defaultValues() {
        this.ManufacturerList.add(new Manufacturer(1, "Cipla"));
        this.ProductList.add(new Product(2, "AZOMYCIN 200"));
        this.ProductList.add(new Product(3, "CEFIX 100"));
        this.ProductList.add(new Product(4, "OMNICLAV 375"));
        this.ProductIdNameMap.put(2, "AZOMYCIN 200");
        this.ProductIdNameMap.put(3, "CEFIX 100");
        this.ProductIdNameMap.put(4, "OMNICLAV 375");
        for (int i = 0; i < ProductList.size(); i++) {
            this.ProductList.get(i).setManufacturer(1);
            this.ManufacturerList.get(0).addProduct(this.ProductList.get(i).id);
        }
        this.ShopList.add(new Shop(5, "Aggarwal Medical Store", 1));
        this.ShopList.add(new Shop(6, "Krishna Medical Hall", 1));
        this.AddCopiesOfProductToShop(5, 2, 400);
        this.AddCopiesOfProductToShop(5, 3, 200);
        this.AddCopiesOfProductToShop(6, 2, 700);
        this.AddCopiesOfProductToShop(6, 4, 500);
        this.CustomerList.add(new Customer(7, "Nikhil Saraswat", 1));
        this.CustomerList.add(new Customer(8, "Rohit Gupta", 2));
        this.DeliveryAgentList.add(new DeliveryAgent(9, "Praveen", 1));
        this.DeliveryAgentList.add(new DeliveryAgent(10, "Arvind", 1));
        this.DeliveryAgentList.add(new DeliveryAgent(11, "Nandan", 2));
        for (int i = 1; i < 12; i++) this.ExistingIDs.add(i);
    }
}

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("\n-------------------------------------------------------------\n----------------Welcome to the Medicine Shop!----------------\n-------------------------------------------------------------\n");
        MasterInterface MI = new MasterInterface(); // declaring an object MI of class MasterInterface
        Scanner scan = new Scanner(System.in); // declaring an object scan of class Scanner for input


        MI.defaultValues(); // this function is optional, we can use this if we want to just check our system with some sample

        // This infinite loop will run until quit
        while (true) {
            System.out.println("\t\t\t\t\tMAIN MENU");
            System.out.println("\nType 1: for creating, deleting or printing any entity");
            System.out.println("Type 2: for adding a product to manufacturer");
            System.out.println("Type 3: for adding certain number of copies of a product to a shop");
            System.out.println("Type 4: for adding an order of a product from a customer ");
            System.out.println("Type 5: for Processing an order ");
            System.out.println("Type 6: for Listing all the purchases made by a customer");
            System.out.println("Type 7: for Listing inventory of a shop (Products and counts) ");
            System.out.println("Type 8: for Listing of Products made by a manufacturer  ");
            System.out.println("Type 9: for Listing of Existing IDs ");
            System.out.println("Type any other number: for QUIT \n");

            int input = scan.nextInt();
            scan.nextLine();

            if (input == 1) {
                System.out.println("\nType 1: for creating any entity");
                System.out.println("Type 2: for deleting any entity");
                System.out.println("Type 3: for printing any entity");
                System.out.println("Type any other number: for BACK TO MAIN MENU");
                int queryType = scan.nextInt();
                scan.nextLine();

                if (queryType == 1) {
                    System.out.println("\nEnter the type of entity");
                    String Entity = scan.nextLine();
                    Entity = Entity.toLowerCase();
                    System.out.println("Enter the name of the Entity : ");
                    String name = scan.nextLine();
                    System.out.println("Enter the id of the Entity : ");
                    int id = scan.nextInt();
                    scan.nextLine();
                    if (Entity.equals("product")) {
                        System.out.println("Enter the id of the manufacturer of this product : ");
                        int manufacturerId = scan.nextInt();
                        scan.nextLine();
                        if (MI.checkID_invalidity(id)) {
                            System.out.println("Sorry, One of the following IDs already exists in our records!");
                            continue;
                        }
                        if (MI.checkProductID(id)) {
                            System.out.println("This product ID already exists!");
                            continue;
                        }
                        if (!(MI.checkManufacturerID(manufacturerId))) {
                            System.out.println("This manufacturer ID does not exist in our records!");
                            continue;
                        }
                        Product product = new Product(id, name);
                        product.setManufacturer(manufacturerId);
                        MI.AddProductToProductList(product);
                        MI.AddProdToManufacturer(id, manufacturerId);
                    } else if (Entity.equals("manufacturer")) {
                        if (MI.checkID_invalidity(id) || MI.checkManufacturerID(id)) {
                            System.out.println("This ID already exists in our records!");
                            continue;
                        }
                        Manufacturer manufacturer = new Manufacturer(id, name);
                        MI.ManufacturerList.add(manufacturer);
                    } else if (Entity.equals("customer")) {
                        if (MI.checkID_invalidity(id) || MI.checkCustomerID(id)) {
                            System.out.println("This ID already exists in our records!");
                            continue;
                        }
                        System.out.println("Enter the zip code of the customer : ");
                        int zip = scan.nextInt();
                        scan.nextLine();
                        Customer customer = new Customer(id, name, zip);
                        MI.CustomerList.add(customer);
                    } else if (Entity.equals("shop")) {
                        if (MI.checkID_invalidity(id) || MI.checkShopID(id)) {
                            System.out.println("This ID already exists in our records!");
                            continue;
                        }
                        System.out.println("Enter the zip code of the shop : ");
                        int zip = scan.nextInt();
                        scan.nextLine();
                        Shop shop = new Shop(id, name, zip);
                        MI.ShopList.add(shop);
                    } else if (Entity.equals("delivery agent")) {
                        if (MI.checkID_invalidity(id) || MI.checkdeliveryAgentID(id)) {
                            System.out.println("This ID already exists in our records!");
                            continue;
                        }
                        System.out.println("Enter the zip code of the delivery agent : ");
                        int zip = scan.nextInt();
                        scan.nextLine();
                        DeliveryAgent deliveryAgent = new DeliveryAgent(id, name, zip);
                        MI.DeliveryAgentList.add(deliveryAgent);
                    } else {
                        System.out.println("Wrong Input!");
                        continue;
                    }
                    MI.AddIDtoExistingIDset(id);
                    System.out.println("Your Entity has been added successfully!");
                } else if (queryType == 2) {
                    System.out.println("\nType the type of entity");
                    String Entity = scan.nextLine();
                    Entity = Entity.toLowerCase();
                    System.out.println("Enter the id of the Entity : ");
                    int id = scan.nextInt();
                    scan.nextLine();
                    if (Entity.equals("product")) {
                        if (!(MI.checkProductID(id))) {
                            System.out.println("This ID does not exist!");
                            continue;
                        }
                        for (int i = 0; i < MI.ProductList.size(); i++) {
                            if (MI.ProductList.get(i).id.equals(id)) {
                                MI.ProductList.remove(i);
                            }
                        }
                        for (int i = 0; i < MI.ManufacturerList.size(); i++) {
                            MI.ManufacturerList.get(i).DeleteProduct(id);
                        }
                        for (int i = 0; i < MI.ShopList.size(); i++) {
                            MI.ShopList.get(i).DeleteProduct(id);
                        }
                    } else if (Entity.equals("manufacturer")) {
                        if (!(MI.checkManufacturerID(id))) {
                            System.out.println("This ID does not exist!");
                            continue;
                        }
                        for (int i = 0; i < MI.ProductList.size(); i++) {
                            if (MI.ProductList.get(i).Manufacturer.equals(id)) {
                                for (int j = 0; j < MI.ShopList.size(); j++) {
                                    MI.ShopList.get(j).DeleteProduct(MI.ProductList.get(i).id);
                                }
                            }
                        }
                        for (int i = 0; i < MI.ManufacturerList.size(); i++) {
                            if (MI.ManufacturerList.get(i).id.equals(id)) {
                                for (Integer ProductID : MI.ManufacturerList.get(i).Products) {
                                    for (int j = 0; j < MI.ProductList.size(); j++) {
                                        if (MI.ProductList.get(j).id.equals(ProductID)) {
                                            MI.ProductList.remove(j);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < MI.ManufacturerList.size(); i++) {
                            if (MI.ManufacturerList.get(i).id.equals(id)) {
                                MI.ManufacturerList.remove(i);
                            }
                        }
                    } else if (Entity.equals("customer")) {
                        if (!(MI.checkCustomerID(id))) {
                            System.out.println("Sorry, This ID does not exist!");
                            continue;
                        }
                        for (int i = 0; i < MI.CustomerList.size(); i++) {
                            if (MI.CustomerList.get(i).id.equals(id)) {
                                MI.CustomerList.remove(i);
                            }
                        }
                    } else if (Entity.equals("shop")) {
                        if (!(MI.checkShopID(id))) {
                            System.out.println("Sorry, This ID does not exist!");
                            continue;
                        }
                        for (int i = 0; i < MI.ShopList.size(); i++) {
                            if (MI.ShopList.get(i).id.equals(id)) {
                                MI.ShopList.remove(i);
                            }
                        }
                    } else if (Entity.equals("delivery agent")) {
                        if (!(MI.checkdeliveryAgentID(id))) {
                            System.out.println("Sorry, This ID does not exist!");
                            continue;
                        }
                        for (int i = 0; i < MI.DeliveryAgentList.size(); i++) {
                            if (MI.DeliveryAgentList.get(i).id.equals(id)) {
                                MI.DeliveryAgentList.remove(i);
                            }
                        }
                    } else {
                        System.out.println("Wrong Input!");
                        continue;
                    }
                    System.out.println("The particular Entity has been deleted!");
                    for (int i = 0; i < MI.ExistingIDs.size(); i++) {
                        if (MI.ExistingIDs.get(i).equals(id)) MI.ExistingIDs.remove(i);
                    }
                } else if (queryType == 3) {
                    System.out.println("\nType the type of entity");
                    String Entity = scan.nextLine();
                    Entity = Entity.toLowerCase();

                    if (Entity.equals("product")) {
                        if (MI.ProductList.size() == 0)
                            System.out.println("Sorry!, There is no product in our Records");
                        else {
                            System.out.println("\nThe List of all products is as following :-");
                            for (Product product : MI.ProductList)
                                System.out.println("ID : " + product.id + ", Name : " + product.name);
                        }
                    } else if (Entity.equals("customer")) {
                        if (MI.CustomerList.size() == 0)
                            System.out.println("Sorry!, There is no customer in our Records");
                        else {
                            System.out.println("\nThe List of all customers is as following :-");
                            for (Customer customer : MI.CustomerList)
                                System.out.println("ID : " + customer.id + ", Name : " + customer.name);
                        }
                    } else if (Entity.equals("manufacturer")) {
                        if (MI.ManufacturerList.size() == 0)
                            System.out.println("Sorry!, There is no manufacturer in our Records");
                        else {
                            System.out.println("The List of all manufacturer is as following :-");
                            for (Manufacturer manufacturer : MI.ManufacturerList) {
                                System.out.println("ID : " + manufacturer.id + ", Name : " + manufacturer.name);
                            }
                        }
                    } else if (Entity.equals("shop")) {
                        if (MI.ShopList.size() == 0) System.out.println("Sorry!, There is no shop in our Records");
                        else {
                            System.out.println("The List of all shops is as following :-");
                            for (Shop shop : MI.ShopList)
                                System.out.println("ID : " + shop.id + ", Name : " + shop.name);
                        }
                    } else if (Entity.equals("delivery agent")) {
                        if (MI.DeliveryAgentList.size() == 0)
                            System.out.println("Sorry!, There is no delivery agent in our Records");
                        else {
                            System.out.println("The List of all delivery agents is as following :-");
                            for (DeliveryAgent deliveryAgent : MI.DeliveryAgentList) {
                                System.out.println("ID : " + deliveryAgent.id + ", Name : " + deliveryAgent.name);
                            }
                        }
                    }
                } else {
                    System.out.println("Wrong Input!");
                    continue;
                }
            } else if (input == 2) {
                System.out.println("Enter the id of the Manufacturer :");
                int idMF = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter the id of the Product : ");
                int idProd = scan.nextInt();
                scan.nextLine();
                if (MI.AddProdToManufacturer(idProd, idMF))
                    System.out.println("\nThe product has been added to the manufacturer");

            } else if (input == 3) {
                System.out.println("Enter the id of the Shop :");
                int idSHOP = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter the id of the Product : ");
                int idProd = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter the new number of copies of the Product : ");
                int copies = scan.nextInt();
                scan.nextLine();
                if (MI.AddCopiesOfProductToShop(idSHOP, idProd, copies))
                    System.out.println("\nThe copies of the product have been added in the inventory list of shop");
            } else if (input == 4) {
                System.out.println("Enter the id of the Customer : ");
                int idCMR = scan.nextInt();
                scan.nextLine();
                Customer CMR = new Customer(-1, "", -1);
                for (Customer customer : MI.CustomerList) {
                    if (customer.id.equals(idCMR)) {
                        CMR = new Customer(customer.id, customer.name, customer.ZipCode);
                    }
                }
                if (CMR.id.equals(-1)) {
                    System.out.println("Sorry, this customer does not exist in our records");
                    continue;
                }
                System.out.println("Enter the id of the Product : ");
                int idProd = scan.nextInt();
                scan.nextLine();
                Product Prod = new Product(-1, "");
                for (Product product : MI.ProductList) {
                    if (product.id == idProd) {
                        Prod = new Product(product.id, product.name);
                    }
                }
                if (Prod.id.equals(-1)) {
                    System.out.println("Sorry, this Product does not exist in our records");
                    continue;
                }
                System.out.println("Enter the number of copies of the product : ");
                int copies = scan.nextInt();
                scan.nextLine();

                if (MI.AddOrder(CMR, Prod, copies)) System.out.println("Order has been added!");

            } else if (input == 5) {
                if (MI.OrderList.isEmpty()) {
                    System.out.println("Sorry!, There is no product in your order list for processing a order");
                    continue;
                }
                System.out.println("Choose any of given orders :-");
                int i = 0;
                for (Order order : MI.OrderList) {
                    System.out.println("Type " + i + ": for Customer ID: '" + order.customer.id + "', Product ID: '" + order.product.id + "', Product copies: " + order.copies + ", ZipCode: " + order.customer.ZipCode);
                    i++;
                }
                int orderNumber = scan.nextInt();
                scan.nextLine();
                boolean result = false;
                if (orderNumber < MI.OrderList.size()) {
                    result = MI.processOrder(MI.OrderList.get(orderNumber));
                }
                if (result) System.out.println("Thank you for ordering from our Medical Shop!");
                else System.out.println("Sorry!, This order can't be processed!");

            } else if (input == 6) {
                System.out.println("Enter the id of the Customer : ");
                int idCMR = scan.nextInt();
                scan.nextLine();
                MI.ListPurchasesOfCustomer(idCMR);
            } else if (input == 7) {
                System.out.println("Enter the id of the Shop : ");
                int idSHOP = scan.nextInt();
                scan.nextLine();
                MI.ListProductsOfInventories(idSHOP);
            } else if (input == 8) {
                System.out.println("Enter the id of the Manufacturer : ");
                int idManufacturer = scan.nextInt();
                scan.nextLine();
                MI.ListProductsOfManufacturer(idManufacturer);
            }
            if (input == 9) {
                if (MI.ExistingIDs.isEmpty()) {
                    System.out.println("Sorry, There is no ID in our records yet!");
                    continue;
                }
                System.out.println("\nThe list of all existing IDs is as following :-");
                for (Integer ID : MI.ExistingIDs)
                    System.out.print(ID + ", ");
                System.out.println();
            }
            if (input > 9 || input < 0) {
                System.out.println("Thank You!");
                break;
            }
        }
    }
}
