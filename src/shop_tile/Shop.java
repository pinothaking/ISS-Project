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
	
	public boolean purchase(Item item, Player player) {
		if(!items.contains(item)){
			return false;
		}
		if (player.getAura() < item.getPrice()) {
			return false;
		}
		player.spendAuraPoint(item.getPrice());
		// qui se si deve mettere inventario
		return true;
	}
}
