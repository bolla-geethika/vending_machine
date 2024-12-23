import java.util.Scanner;

public class VendingMachine {
    private final Inventory inventory;
    private final Cart cart;
    private static final int DEFAULT_RESTOCK_QUANTITY = 10; // Default quantity for restocking

    public VendingMachine() {
        this.inventory = new Inventory();
        this.cart = new Cart();
        initializeInventory();
    }

    private void initializeInventory() {
        inventory.addProduct(new Product("Chips", 10), DEFAULT_RESTOCK_QUANTITY);
        inventory.addProduct(new Product("Soda", 15), DEFAULT_RESTOCK_QUANTITY);
        inventory.addProduct(new Product("Chocolate", 20), DEFAULT_RESTOCK_QUANTITY);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Cart getCart() {
        return cart;
    }

    private void refillInventoryIfNeeded() {
        boolean needsRefill = inventory.getInventory().values().stream()
                .anyMatch(quantity -> quantity <= 2); // Refill if any product is low

        if (needsRefill) {
            System.out.println("\n[ALERT] Low stock detected. Automatically restocking...");
            inventory.getInventory().replaceAll((k, v) -> v <= 2 ? DEFAULT_RESTOCK_QUANTITY : v);
        }
    }

    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Automatic inventory check and refill
            vendingMachine.refillInventoryIfNeeded();

            System.out.println("\nVending Machine Menu:");
            System.out.println("1. Display Products");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. Remove Item from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            // Validate input to ensure the user enters a valid integer for menu choice
            int choice = -1;
            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    break; // Valid input, break out of the loop
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 6.");
                    scanner.nextLine(); // Consume invalid input
                }
            }

            switch (choice) {
                case 1:
                    vendingMachine.getInventory().displayInventory();
                    break;
                case 2:
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    Product selectedProduct = null;
                    for (Product product : vendingMachine.getInventory().getInventory().keySet()) {
                        if (product.getName().equalsIgnoreCase(productName)) {
                            selectedProduct = product;
                            break;
                        }
                    }

                    if (selectedProduct == null) {
                        System.out.println("Product not found.");
                    } else {
                        int quantity = -1;
                        // Validate the quantity input
                        while (quantity <= 0) {
                            System.out.print("Enter quantity: ");
                            if (scanner.hasNextInt()) {
                                quantity = scanner.nextInt();
                                scanner.nextLine(); // Consume newline
                                if (quantity <= 0) {
                                    System.out.println("Quantity must be greater than zero.");
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a valid quantity.");
                                scanner.nextLine(); // Consume invalid input
                            }
                        }

                        if (vendingMachine.getInventory().isAvailable(selectedProduct, quantity)) {
                            vendingMachine.getCart().addToCart(selectedProduct, quantity);
                            System.out.println("Added to cart: " + selectedProduct.getName());
                        } else {
                            System.out.println("Insufficient stock for " + selectedProduct.getName());
                        }
                    }
                    break;
                case 3:
                    if (vendingMachine.getCart().getCartItems().isEmpty()) {
                        System.out.println("Your cart is empty.");
                        break;
                    }
                    System.out.print("Enter product name to remove: ");
                    String removeProductName = scanner.nextLine();
                    Product removeProduct = null;
                    for (Product product : vendingMachine.getCart().getCartItems().keySet()) {
                        if (product.getName().equalsIgnoreCase(removeProductName)) {
                            removeProduct = product;
                            break;
                        }
                    }

                    if (removeProduct == null) {
                        System.out.println("Product not found in cart.");
                    } else {
                        int removeQuantity = -1;
                        // Validate the quantity to remove
                        while (removeQuantity <= 0) {
                            System.out.print("Enter quantity to remove: ");
                            if (scanner.hasNextInt()) {
                                removeQuantity = scanner.nextInt();
                                scanner.nextLine(); // Consume newline
                                if (removeQuantity <= 0) {
                                    System.out.println("Quantity must be greater than zero.");
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a valid quantity.");
                                scanner.nextLine(); // Consume invalid input
                            }
                        }
                        vendingMachine.getCart().removeFromCart(removeProduct, removeQuantity);
                        System.out.println("Removed from cart: " + removeProduct.getName());
                    }
                    break;
                case 4:
                    vendingMachine.getCart().displayCart();
                    System.out.println("Cart Total: " + vendingMachine.getCart().calculateTotal());
                    break;
                case 5:
                    double totalAmount = vendingMachine.getCart().calculateTotal();
                    if (totalAmount == 0) {
                        System.out.println("Your cart is empty.");
                        break;
                    }
                    System.out.println("Total Amount to Pay: " + totalAmount);
                    double payment = -1;
                    // Validate payment input
                    while (payment < totalAmount) {
                        System.out.print("Enter payment amount: ");
                        if (scanner.hasNextDouble()) {
                            payment = scanner.nextDouble();
                            scanner.nextLine(); // Consume newline
                            if (payment < totalAmount) {
                                System.out.println("Insufficient payment. Please enter a larger amount.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a valid payment amount.");
                            scanner.nextLine(); // Consume invalid input
                        }
                    }
                    System.out.println("Payment successful. Dispensing items...");
                    for (var entry : vendingMachine.getCart().getCartItems().entrySet()) {
                        vendingMachine.getInventory().reduceProduct(entry.getKey(), entry.getValue());
                    }
                    vendingMachine.getCart().getCartItems().clear();
                    System.out.println("Change returned: " + (payment - totalAmount));
                    break;
                case 6:
                    System.out.println("Thank you for using the vending machine. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
