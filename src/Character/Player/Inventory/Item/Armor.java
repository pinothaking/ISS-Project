package Character.Player.Inventory.Item;

import Character.Player.Inventory.Item.Enumeration.ItemRarity;

public class Armor extends Item {
    private Integer HpBoost;
    private int armorId;

    public Armor(String name, ItemRarity rarity, Integer ObjectId, boolean isStackable, 
    String fusionKey, Integer HpBoost, int armorId) {
        super(name, rarity, ObjectId, false, "a:" + armorId + ":" + rarity);
        this.HpBoost = HpBoost;
        this.armorId = armorId;
    }

    public Integer getHpBoost() {
        return HpBoost;
    }

    public int getArmorId() {
        return armorId;
    }

    private void levelUp () {

    }

    @Override
    public void onDuplicate(){
        levelUp();
    }
}