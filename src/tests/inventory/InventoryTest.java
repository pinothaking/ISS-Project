package tests.inventory;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    
    private List<ItemTest> items = new ArrayList<>(); 
    private ItemTest OGGETTO1;
    private ItemTest OGGETTO2;

    
    public InventoryTest (){
        OGGETTO1 = new ItemTest(WeaponTypeTest.SWORD, ItemRarityTest.COMMON);
        OGGETTO2 = new ItemTest(WeaponTypeTest.AXE, ItemRarityTest.RARE);

        items.add(OGGETTO1);
        items.add(OGGETTO2);
    }
    @Test
    private void addItem (ItemTest item) {
        items.add(item);
    }

    @Test
    private void removeItem(ItemTest item) {
        items.remove(item);
    }

    @Test
    public List<ItemTest> getItems() {
    return new ArrayList<>(items);
    }

}
