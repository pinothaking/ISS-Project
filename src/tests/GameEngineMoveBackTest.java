package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Character.Player.Player;
import game_engine.GameEngine;
import game_map_state.GameMap;
import game_map_state.GameMapTile;
import game_map_state.MapFactory;
import game_map_state.Sectors;

import java.util.ArrayList;
import java.util.List;

public class GameEngineMoveBackTest {

    private GameEngine engine;
    private GameMap map;
    private Player player;
    private List<GameMapTile> allTiles;

    @BeforeEach
    void setup() {
        engine = new GameEngine();
        map = new MapFactory().createMap();

        // lista ordinata di tutte le tile
        allTiles = new ArrayList<>();
        for (Sectors s : map.getSectors()) {
            allTiles.addAll(s.getTiles());
        }

        // player inizialmente sulla tile index 3
        player = new Player(
                1,
                "TestPlayer",
                allTiles.get(3),
                100, // hp
                50, // aura
                30 // spirit
        );
    }

    @Test
    void moveBackMovesPlayerCorrectly() {
        engine.moveBack(player, 2, map);

        assertEquals(allTiles.get(1), player.getPosition());
    }

    @Test
    void moveBackDoesNotGoBelowZero() {
        engine.moveBack(player, 10, map);

        assertEquals(allTiles.get(0), player.getPosition());
    }

    @Test
    void moveBackKeepsPlayerOnMap() {
        engine.moveBack(player, 1, map);

        assertNotNull(player.getPosition());
        assertTrue(allTiles.contains(player.getPosition()));
    }
}
