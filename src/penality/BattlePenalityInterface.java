package penality;
import Character.Player.Player;
import game_engine.*;
import game_map_state.GameMap;

public interface BattlePenalityInterface {
    void execute(GameEngine gameEngine, Player player, GameMap map);

} 
