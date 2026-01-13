package Character.Player.Inventory;

import Character.Player.Inventory.Item.Item;
import stats_change.Command;

public class OpenInventoryButton implements Command {

    private final Inventory inventory;

    public OpenInventoryButton(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void execute() {
        System.out.println("Apri inventario!");
        for (Item item : inventory.getItems()) {
            System.out.println("- " + item.getName());
        }
        System.out.println("----------------------");
    }
}
