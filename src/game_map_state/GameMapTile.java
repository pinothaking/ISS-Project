package game_map_state;

import battle_exploration_tiles.*;

public class GameMapTile {

    private Tile tileBehavior; // comportamento (battle, shop ecc.)
    private int id;
    private boolean hasPlayer;

    public GameMapTile(Tile tileBehavior, int id, boolean hasPlayer) {
        this.tileBehavior = tileBehavior;
        this.id = id;
        this.hasPlayer = hasPlayer;
    }

    public void interact(GameContext context) {
        tileBehavior.interact(context);
    }

    public int getId() {
        return id;
    }

    public boolean hasPlayer() {
        return hasPlayer;
    }

    public void setPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    public Tile getTileBehavior() {
        return tileBehavior;
    }
}
