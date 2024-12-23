import java.util.concurrent.ConcurrentHashMap;

public class Inventory {
    private final ConcurrentHashMap<Product, Integer> inventory = new ConcurrentHashMap<>();

    public void addProduct(Product product, int quantity) {
        inventory.merge(product, quantity, Integer::sum);
    }

    public boolean isAvailable(Product product, int quantity) {
        return inventory.getOrDefault(product, 0) >= quantity;
    }

    public boolean reduceProduct(Product product, int quantity) {
        if (isAvailable(product, quantity)) {
            inventory.put(product, inventory.get(product) - quantity);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("Available Products:");
        inventory.forEach((product, quantity) -> System.out
                .println(product.getName() + " - Price: " + product.getPrice() + ", Quantity: " + quantity));
    }

    public ConcurrentHashMap<Product, Integer> getInventory() {
        return inventory;
    }
}
