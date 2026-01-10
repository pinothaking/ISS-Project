package game_engine;


import java.util.ArrayList;
import java.util.List;

import Character.Player.Player;
import game_map_state.GameMap;
import game_map_state.GameMapTile;
import game_map_state.Sectors;

public class GameEngine {

    public void movePlayer(Player player, int steps, GameMap map) {
        
        List<GameMapTile> allTiles = new ArrayList<>();
        for (Sectors s : map.getSectors()) {
            allTiles.addAll(s.getTiles());  
        }

        int currentIndex = allTiles.indexOf(player.getPosition());
        int newIndex = currentIndex + steps;

        if (newIndex >= allTiles.size()) {
            newIndex = allTiles.size() - 1;
        }

        player.moveTo(allTiles.get(newIndex));

        System.out.println(player.getName() + " si trova ora sulla tile " + player.getPosition().getId());

        for (Sectors s : map.getSectors()) {
            if (s.containsPlayer(player)) {
                System.out.println("Player nel settore: " + s.getName());
            }
        }
    }

}
