package Roll_Dice_Player;

import game_map_state.GameMapTile;

public class Player {

    private int id;           // ID univoco
    private String name;
    private GameMapTile id_tile;
    private int hp;
    private int aura;
    private int spirit;

    public Player(int id, String name, GameMapTile id_tile, int hp, int aura, int spirit) {
        this.id = id;
        this.name = name;
        this.id_tile = id_tile;
        this.hp = hp;
        this.aura = aura;
        this.spirit = spirit;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GameMapTile getPosition() {
        return id_tile;
    }

    public int getHp() {
        return hp;
    }

    public int getAura() {
        return aura;
    }

    public int getSpirit() {
        return spirit;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAura(int aura) {
        this.aura = aura;
    }

    public void setSpirit(int spirit) {
        this.spirit = spirit;
    }

    public void moveTo(GameMapTile newTile) {
        this.id_tile.setPlayer(false);
        this.id_tile = newTile;
        this.id_tile.setPlayer(true);
    }
}