package Roll_Dice_Player;

import game_engine.GameEngine;
import game_map_state.GameMap;


public class RollDice extends AbstractCommand {
	private final Player player;
	private final DiceEq diceEq;
	private final diceMove diceMove;
	private final GameEngine engine;
	private final GameMap map;

	public RollDice(Player player, DiceEq diceEq, diceMove diceMove, GameEngine engine, GameMap map) {
		this.player = player;
		this.diceEq = diceEq;
		this.diceMove = diceMove;
		this.engine = engine;
		this.map = map;
	}

	@Override
	public void execute() {
		int equip = diceEq.roll();
		int move = diceMove.roll();
		int total = equip + move;

		System.out.println(player.getName() + " tira i dadi: Equip= " + equip + " Move= " + move);
		engine.movePlayer(player, total, map); 
	}

	public int roll() {
		return diceEq.roll() + diceMove.roll();
	}
}
