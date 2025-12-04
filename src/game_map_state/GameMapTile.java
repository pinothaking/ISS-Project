package game_map_state;

import battle_exploration_tiles.*; // import tiles for pkg

public class GameMapTile {
    private Tile tileBehaivor; // describes which special tile is ex Battle , Shop ecc..
    private int id;
    private boolean hasPlayer = false; 

    public GameMapTile(Tile tileBehaivor , int id , boolean hasPlayer){ // costruction
        this.tileBehaivor = tileBehaivor;
        this.id  = id;
        this.hasPlayer = hasPlayer;
    }

    public void interact(GameContext context){
        tileBehaivor.interact(context);
    }

    public int getId(){
        return id; 
    }
    public boolean hasPlayer(){
        return hasPlayer;
    }

    public void setPlayer(boolean isPlayer){
        this.hasPlayer = isPlayer;
    }
}
