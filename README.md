# Online_Medical_Shop_Manager
This was the JAVA Project Of Software Engineering Lab at IIT Kharagpur

Problem Statement: -

In an online medicine shop, there are the following set of relevant entities:
    • Manufacturer
    • Customer
    • Product
    • Shops and Warehouses
    • Delivery agent.
All entities have a unique id (int), and a name (String). Additionally, a manufacturer has a set of
products which it can manufacture. Each product has a manufacturer who can manufacture it.
Customers have a zip code (int) and a list of purchased products, which are initially empty. Shops
and Warehouses have a zipcode (int), an inventory of the number of available copies of all
products (list of java tuples)). A delivery agent has a zipcode (int), and the number of products
delivered.
Your task is to design and implement a java program following the object oriented paradigm, and
implementing the following functionalities:
    1. Create, delete and print entities of each type.
    2. Add a product to manufacturer.
    3. Add a certain number of copies of a product to a shop.
    4. Add an order of a product from a customer.
    5. Process an order (can be satisfied only if the product is available at a shop in the
    customer’s zipcode). Also assign a delivery agent who has delivered the least number
    products.
    6. List all the purchases made by a customer.
    7. List inventory of a shop (Products and counts).
    8. Products made by a manufacturer.
    9. A master text-based interface to access all the above functionalities. 
