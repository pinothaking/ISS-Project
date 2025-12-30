package shop_tile;

public class Player {
	/**
	 * private int AuraPoints;
	 * private Tile curretTile;
	 * 
	 * 
	 * public Player(){
	 * 	this.auraPoints = 0;
	 *}
	 *     public void setCurrentTile(Tile tile) {
        this.currentTile = tile;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public int getAuraPoints() {
        return auraPoints;
    }

    public void addAuraPoints(int amount) {
        this.auraPoints += amount;
    }

    public void spendAuraPoints(int amount) {
        this.auraPoints -= amount;
    }

    public void openShop(ShopMenu shopMenu) {
        if (currentTile != null && currentTile.isShopTile()) {
            shopMenu.show(this);
        } else {
            System.out.println("Non sei su una shop tile!");
        }
    }
	 *
	 */
}
