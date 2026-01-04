package shop_tile;
import java.util.List;
import Character.Player.Player;


public class Shop{
	public List<Item>items;
	
	public Shop(List<Item>items) {
		this.items = items;
	}
	
	public List<Item> getItems(){
		return items;
	}
	
	public boolean purchase(Item item, shop_tile.Player player) {
		if(!items.contains(item)){
			return false;
		}
		if (Player.getAura() < item.getPrice()) {
			return false;
		}
		Player.spendAuraPoint(item.getPrice());
		// qui se si deve mettere inventario
		return true;
	}
}
