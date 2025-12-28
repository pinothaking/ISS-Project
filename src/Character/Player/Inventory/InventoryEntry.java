package Character.Player.Inventory;

import Character.Player.Inventory.Item.Item;

public class InventoryEntry {
    private int quantity;
    private final Item item;

    public InventoryEntry(Item item) {
        this.quantity = 1;
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public Item getItem() {
        return item;
    }

    void addOne(int quantity) {
        this.quantity++;
    }
    void removeone(int quantity) {
        this.quantity--;
    }
}
