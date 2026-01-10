package penality;

import game_engine.GameEngine;
import Character.Player.Player;
import game_map_state.GameMap;

public class MoveBackPenalty implements BattlePenalityInterface {

    private int steps;

    public MoveBackPenalty(int steps) {
        this.steps = steps;
    }

    @Override
    public void execute(GameEngine gameEngine, Player player, GameMap map) {
        gameEngine.moveBack(player, steps, map);
    }
}
