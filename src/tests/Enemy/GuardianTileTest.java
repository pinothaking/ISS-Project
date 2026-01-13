package battle_exploration_tiles;
import battle_exploration_tiles.*;
import game_map_state.GameMapTile;
import Character.Player.*;
import Character.Enemy.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GuardianTileTest {
    private Player player;
    private Enemy guardian;
    private GameContext context;
    private GuardianTile guardianTile;

    @BeforeEach
    public void setUp() {
        player = new Player(0, "Giocatore1", new GameMapTile(guardianTile, 0, true), 100, 0, 0);
        guardian = new Enemy(0, 10, 10);
        context = new GameContext();
        guardianTile = new GuardianTile(guardian);
    }

    @Test
    public void testPlayerWithEnoughSP_ShouldPayAndPass() {
        // Arrange
        player.setSpirit(50);
        
        // Act
        guardianTile.interact(context, player);
        
        // Assert
        assertTrue(context.isExploration(), 
                  "Lo stato dovrebbe essere ExplorationState dopo il pagamento");
        
        assertEquals(7, player.getSpirit(), 
                    "Gli SP dovrebbero essere 50 - 43 = 7");
    }

    @Test
    public void testPlayerWithoutEnoughSP_ShouldFight() {
        // Arrange
        player.setSpirit(20);
        
        // Act
        guardianTile.interact(context, player);
        
        // Assert
        assertFalse(context.isExploration(), 
                   "Lo stato dovrebbe essere BattleState quando non può pagare");
        
        assertEquals(20, player.getSpirit(), 
                    "Gli SP non dovrebbero essere modificati");
    }

    @Test
    public void testPlayerWithExactSP_ShouldPayAndPass() {
        // Arrange
        player.setSpirit(43);
        
        // Act
        guardianTile.interact(context, player);
        
        // Assert
        assertTrue(context.isExploration(), 
                  "Lo stato dovrebbe essere ExplorationState con SP esatti");
        
        assertEquals(0, player.getSpirit(), 
                    "Gli SP dovrebbero essere 0 dopo il pagamento");
    }
}