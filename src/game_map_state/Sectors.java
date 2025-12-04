package game_map_state;
import java.util.List;
import Roll_Dice_Player.Player;

public class Sectors {
    private String name;
    private List<GameMapTile> tiles;

    public Sectors(String name , List<GameMapTile> tiles){
        this.name = name;
        this.tiles = tiles;
    }

    public String getName(){
        return name;
    }
    
    public List<GameMapTile> getTiles(){
        return tiles;
    }

    public boolean containsPlayer(Player player){
        return tiles.contains(player.getPosition());
    }
}
