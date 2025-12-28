package Character.Player.Inventory.Item;

import Character.Player.Inventory.Item.Enumeration.ItemRarity;

public class Weapon extends Item {
    private Integer damage;
    private int weaponId;

    public Weapon (String name, ItemRarity rarity, Integer ObjectId, boolean isStackable, String fusionKey,
    Integer damage, int weaponId) {
        super(name, rarity, ObjectId, false, "w:" + weaponId + ":" + rarity);
        this.damage = damage;
        this.weaponId = weaponId;
    }

    public Integer getDamage() {
        return damage;
    }

    public int getWeaponId() {
        return weaponId;
    }
}