package penality;

import Character.Player.Player;
import game_engine.GameEngine;
import game_map_state.GameMap;

public class DeathPenality implements BattlePenalityInterface{
    
    @Override
    public void execute(GameEngine gameEngine, Player player, GameMap map) {
        System.out.println(player.getName() + " è morto.");

    }
}
