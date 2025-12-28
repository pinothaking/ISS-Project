package Character.Player.Item;

import Character.Player.Item.Enumeration.ItemRarity;

public class Armor extends Item {
    private Integer HpBoost;

    public Armor(String name, ItemRarity rarity, Integer ObjectId, Integer HpBoost) {
        super(name, rarity, ObjectId);
        this.HpBoost = HpBoost;
    }

    public Integer getHpBoost() {
        return HpBoost;
    }
}