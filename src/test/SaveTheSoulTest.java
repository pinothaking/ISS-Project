import static org.junit.jupiter.api.Assertions.*;

import battle_exploration_tiles.GameContext;
import battle_exploration_tiles.Tile;
import game_map_state.GameMapTile;
import org.junit.jupiter.api.Test;

import Character.Player.Player;
import stats_change.SaveSoulCommand;
import stats_change.Receiver;

class SaveSoulCommandTest {

    @Test
    void execute_shouldIncreaseSpiritAndAura() {

        //creo quello che mi serve per creare un tipo GameMapTile
        Tile testTile = new Tile() {
            @Override
            public void interact(GameContext context) {
                // niente da fare, serve solo per il test
            }
        };

        GameMapTile tile = new GameMapTile(testTile, 1, false);

        //ora posso dare un valore dentro player
        Player player = new Player(1111, "Hero", tile, 100, 10, 5);
        Receiver receiver = new Receiver(player);

        SaveSoulCommand command = new SaveSoulCommand(receiver, 3, 2);

        // Act
        command.execute();

        // Assert
        assertEquals(8, player.getSpirit()); // 5 + 3
        assertEquals(12, player.getAura());  // 10 + 2
    }
}
