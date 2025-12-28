package Character.Player.Inventory.Item;

import Character.Player.Inventory.Item.Enumeration.ItemRarity;

public class Consumable extends Item{
    private String effect;
    private int consumableId;

    public Consumable(String name, ItemRarity rarity, Integer ObjectId, boolean isStackable, 
        String fusionKey, String effect, int consumableId) {
        super(name, rarity, ObjectId, true, "c:" + consumableId + ":" + rarity);
        this.effect = effect;
        this.consumableId = consumableId;
    }

    public String getEffect() {
        return effect;
    }

    public int getConsumableId() {
        return consumableId;
    }
}