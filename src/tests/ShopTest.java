package tests;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;



public class ShopTest {
    @Test
    void purchase(){
        Player player = new Player(100);
        Item AuraJacket = new Item("AuraJacket", 50);
        Shop shop = new Shop(List.of(AuraJacket));

        boolean result = shop.purchase(AuraJacket, player);

        assertTrue(result);
        assertEquals(50, player.getAuraPoints()); // 100 - 50
    }

    @Test
    void purchaseFailWhenIsNotInShop(){
        Player player = new Player(100);
        Item sword = new Item("Sword",50);
        Item shield = new Item("Shield", 30);
        Shop shop = new Shop(List.of(sword));

        boolean result = shop.purchase(shield, player);

        assertFalse(result);
        assertEquals(100, player.getAuraPoints()); 
    }

    @Test
    void purchaseFailNoAura(){
        Player player = new Player(20);
        Item sword = new Item("Sword", 50);
        Shop shop = new Shop(List.of(sword));

        boolean result = shop.purchase(sword, player);
        assertFalse(result);
        assertEquals(20, player.getAuraPoints());
    }

}
