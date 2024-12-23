import java.util.HashMap;

public class Cart {
    private final HashMap<Product, Integer> cart = new HashMap<>();

    public void addToCart(Product product, int quantity) {
        cart.merge(product, quantity, Integer::sum);
    }

    public void removeFromCart(Product product, int quantity) {
        if (cart.containsKey(product)) {
            int currentQuantity = cart.get(product);
            if (currentQuantity <= quantity) {
                cart.remove(product);
            } else {
                cart.put(product, currentQuantity - quantity);
            }
        }
    }

    public void displayCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Items in your cart:");
            cart.forEach((product, quantity) -> System.out
                    .println(product.getName() + " - Quantity: " + quantity + ", Price: " + product.getPrice()));
        }
    }

    public double calculateTotal() {
        return cart.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public HashMap<Product, Integer> getCartItems() {
        return cart;
    }
}
