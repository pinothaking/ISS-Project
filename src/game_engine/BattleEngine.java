package game_engine;

import penality.*;
import Character.Player.*;
import game_map_state.*;
public class BattleEngine {

    private BattlePenalityInterface losePenalty;

    public BattleEngine(BattlePenalityInterface losePenalty) {
        this.losePenalty = losePenalty;
    }

    public void resolve(boolean playerWon,
            GameEngine gameEngine,
            Player player,
            GameMap map) {

        if (!playerWon) {
            losePenalty.execute(gameEngine, player, map);
        }
    }
}

/*
 * BattlePenaltyInterface penalty = new MoveBackPenalty(3);
 * BattleEngine battleEngine = new BattleEngine(penalty);
 * 
 * battleEngine.resolve(false, gameEngine, player, map);
 * 
 * 
 * 
 */