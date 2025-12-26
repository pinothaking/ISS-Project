package game_map_state;

import java.util.List;
import Roll_Dice_Player.Player;

public class Sectors {

    private List<GameMapTile> tiles;
    private String name;

    public Sectors(String name, List<GameMapTile> tiles) {
        this.name = name;
        this.tiles = tiles;
    }

    public List<GameMapTile> getTiles() {
        return tiles;
    }

    public String getName() {
        return name;
    }

    
    public boolean containsPlayer(Player player) {
        int playerTileId = player.getPosition().getId();
        return tiles.stream().anyMatch(t -> t.getId() == playerTileId);
    }

}
