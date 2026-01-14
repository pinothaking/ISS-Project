package Character.Player;

import Character.Player.Inventory.Inventory;
import Character.Player.Inventory.OpenInventoryButton;
import game_map_state.GameMapTile;

public class Player {

    private int id;
    private String name;
    private GameMapTile id_tile;
    private int hp;
    private int aura;
    private int spirit;

    private Inventory inventory;
    //private Button eButton;

    public Player(int id, String name, GameMapTile id_tile, int hp, int aura, int spirit) {
        this.id = id;
        this.name = name;
        this.id_tile = id_tile;
        this.hp = hp;
        this.aura = aura;
        this.spirit = spirit;

        this.inventory = new Inventory();

        // Button “E” per aprire inventario
       // OpenInventoryButton openCommand = new OpenInventoryButton(this.inventory);
        //this.eButton = new Button(openCommand);
    }

    // --- Getters e Setters ---
    public int getId() { return id; }
    public String getName() { return name; }
    public GameMapTile getPosition() { return id_tile; }
    public int getHp() { return hp; }
    public int getAura() { return aura; }
    public int getSpirit() { return spirit; }

    public void setHp(int hp) { this.hp = hp; }
    public void setAura(int aura) { this.aura = aura; }
    public void setSpirit(int spirit) { this.spirit = spirit; }
    public Inventory getInventory() { return inventory; }

    public void spendAuraPoint(int amount) { this.aura -= amount; }
    public void addAuraPoints(int amount) { this.aura += amount; }

    public void moveTo(GameMapTile newTile) {
        this.id_tile.setPlayer(false);
        this.id_tile = newTile;
        this.id_tile.setPlayer(true);
    }

    // --- Metodo per premere il tasto “E” ---
   
}
