# Vending Machine System

## Overview

This project is a simulation of a vending machine system. It allows a user to browse products, add items to a shopping cart, remove items from the cart, checkout, and process payments. The system includes an inventory with products and automatically handles low stock alerts and restocking. The user interacts with the system through a console menu.

## Features

1. **Product Management**: 
   - Display available products in the inventory.
   - Add products to the cart and remove them.
   - Check product availability and manage inventory levels.

2. **Cart Management**: 
   - Add/remove products to/from the cart.
   - View cart contents and total price.
   - Checkout and process payment, including change calculation.

3. **Inventory Management**: 
   - Automatic restocking when stock runs low.
   - Update inventory when products are purchased.

4. **Input Validation**: 
   - Validates user input to ensure correct product selection, quantity input, and payment amounts.

## Project Structure

1. **Product.java**: Defines the `Product` class with name and price attributes.
2. **Cart.java**: Manages the shopping cart, adding/removing items, displaying the cart, and calculating totals.
3. **Inventory.java**: Manages the available products, including adding products, checking availability, and reducing stock.
4. **VendingMachine.java**: The main class that runs the vending machine system. It includes the user interface and handles interactions between the inventory and the cart.

## Getting Started

To get started, you need to have **Java** installed on your machine. You can compile and run the Java code using any Java IDE (such as IntelliJ IDEA or Eclipse) or through the command line.

### Prerequisites

- Java 8 or higher installed on your system.
- A basic understanding of Java programming.

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/vending-machine.git
Navigate to the project directory:

2. Navigate to the project directory:
cd vending-machine

3. Compile the Java files:
javac *.java

4.Run the VendingMachine program:
java VendingMachine

