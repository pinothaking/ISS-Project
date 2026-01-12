package tests.inventory;

public class ItemTest {
    private WeaponTypeTest type;
    private ItemRarityTest rarity;

    public ItemTest(WeaponTypeTest type, ItemRarityTest rarity) {
        this.type = type;
        this.rarity = rarity;
    }
}
