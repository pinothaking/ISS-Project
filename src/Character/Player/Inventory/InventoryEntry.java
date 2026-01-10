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

    void addOne() {
        this.quantity++;
    }
    void removeone() {
        this.quantity--;
    }
}
