package Character.Player.Inventory.Item;

import Character.Player.Inventory.Item.Enumeration.ItemRarity;

public class Item {
    private String name;
    private ItemRarity rarity;
    private Integer ObjectId;
    private boolean isStackable;
    private final String fusionKey;

    public Item (String name, ItemRarity rarity, Integer ObjectId, boolean isStackable, 
    String fusionKey) {
        this.name = name; /*univoco per fondere, ma così non si può modificare :/*/
        this.rarity = rarity;
        this.ObjectId = ObjectId;
        this.isStackable = isStackable;
        this.fusionKey = fusionKey;
    }

    public String getName() {
        return this.name;
    } 

    public ItemRarity getRarity() {
        return this.rarity;
    }

    public Integer getObjectId() {
        return this.ObjectId;
    }

    public boolean isStackabl() {
        return isStackable;
    }

    public String getFusionKey() {
    return fusionKey;
}

    /*public void setName(String name) {
        
    }*/
}