package Character.Player.Inventory;

import java.util.*;

import Character.Player.Inventory.Item.Item;

public class Inventory {
    private final List<Item> items = new ArrayList<>();
    Map<String, InventoryEntry> entriesByKey;

    public void addItem(Item item) {
        String key = item.getFusionKey();
        InventoryEntry entry = entriesByKey.get(key);

         if (entry == null) {
        entriesByKey.put(key, new InventoryEntry(item));
        }

    // 4) se esiste già:
    //    - stackable => aumento quantità
    //    - non stackable => fusione/upgrade (armi/armature)
        if (item.isStackable()) {
                entry.addOne();
        } else {
            entry.getItem().onDuplicate();
        }
    }

    public List<Item> getItems() {
        return items;
    }
}