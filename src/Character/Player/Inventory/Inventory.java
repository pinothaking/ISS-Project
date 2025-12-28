package Character.Player.Inventory;

import java.util.*;

import Character.Player.Inventory.Item.Item;

public class Inventory {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
        
        
    }

    public List<Item> getItems() {
        return items;
    }
}