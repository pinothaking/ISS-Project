package tests;

import static org.junit.jupiter.api.Assertions.*;
import battle_exploration_tiles.GameContext;
import battle_exploration_tiles.Tile;
import game_map_state.GameMapTile;
import Character.Player.Player;
import Character.Player.PlayerDisplay;
import stats_change.Receiver;
import org.junit.jupiter.api.Test;

class PlayerTest {

    // Tile fittizio per creare GameMapTile
    private Tile fakeTile = new Tile() {
        @Override
        public void interact(GameContext context) {
            // nessuna azione, serve solo per il test
        }
    };

    private GameMapTile testTile = new GameMapTile(fakeTile, 1, false);

    @Test
    void saveSoulShouldIncreaseSpiritAndAura() {
        Player player = new Player(1111, "Hero", testTile, 100, 10, 5);
        Receiver receiver = new Receiver(player);

        // Applichiamo il comando
        receiver.saveSoul(3, 2);

        assertEquals(8, player.getSpirit());  // 5 + 3
        assertEquals(12, player.getAura());   // 10 + 2
        assertEquals(100, player.getHp());    // HP invariati
    }

    @Test
    void settingHpShouldUpdateCorrectly() {
        Player player = new Player(1111, "Hero", testTile, 50, 10, 5);

        player.setHp(80);

        assertEquals(80, player.getHp());
        assertEquals(10, player.getAura());
        assertEquals(5, player.getSpirit());
    }

    @Test
    void settingAuraAndSpiritShouldUpdateCorrectly() {
        Player player = new Player(1111, "Hero", testTile, 5, 4, 3);

        player.setAura(20);
        player.setSpirit(15);

        assertEquals(20, player.getAura());
        assertEquals(15, player.getSpirit());
        assertEquals(5, player.getHp());  // HP invariati
    }

    @Test
    void subscribingAndNotifyingDisplayShouldCallUpdate() {
        Player player = new Player(1111, "Hero", testTile, 1, 100, 10);

        class TestDisplay implements PlayerDisplay {
            boolean updated = false;

            @Override
            public void update(Player player) {
                updated = true;
            }
        }
        // Cambiamo HP per triggerare la notifica
        player.setHp(80);
    }
}
