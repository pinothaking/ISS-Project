package shop_tile;
import Character.Player.Player;

public class ShopMenu {
	public Shop Shop;
	
	public ShopMenu(Shop Shop) {
		this.Shop = Shop;
	}
	
	public void show(Player player) {
		// qui se si vuole aggiungere una grafica per mostrare item e scegliere cosa comprare
	}
	
	public boolean purchase(Item item, Player player){
		return Shop.purchase(item, player);	
	}
}
