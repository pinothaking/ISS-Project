package tests;

import stats_change.*;
import Character.Enemy.Command;
import Character.Player.Player;
import battle_exploration_tiles.GameContext;
import battle_exploration_tiles.Tile;
import game_map_state.GameMapTile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    Tile testTile = new Tile() {
            @Override
            public void interact(GameContext context) {
                // niente da fare, serve solo per il test
            }
        };

    @Test
    void testStealSoulCommand() {
        // Setup
        Player p = new Player(1111, "Hero", new GameMapTile(testTile, 0, false), 100, 10, 5);
        Command stealCmd = new ConsumeSoul();

        // Esecuzione diretta del comando (senza bottone)
        stealCmd.execute();

        // Verifica: Se il comando ha funzionato, il Player deve essere cambiato
        assertEquals(-1, p.getSpirit());
    }
    
    @Test
    void testSaveSoulCommand() {
        Player p = new Player(1111, "Hero", new GameMapTile(testTile, 0, false), 100, 10, 5);
        Receiver r = new Receiver(p);
        Command saveCmd = new SaveSoulCommand(r, 10, 10);

        saveCmd.execute();

        assertEquals(1, p.getSpirit());
    }
}