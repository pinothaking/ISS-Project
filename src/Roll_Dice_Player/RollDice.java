package Roll_Dice_Player;

public class RollDice extends Command{
	private Player player;
	private DiceEq diceEq;
	private diceMove diceMove;
	
	public RollDice(Player player, DiceEq diceEq, diceMove diceMove) {
		
		this.player = player;
		this.diceEq = diceEq;
		this.diceMove = diceMove;
	}
	
	@Override
	
	public void execute() {
		int equip = diceEq.roll();
		int move = diceMove.roll();
		int total = equip + move;
		
		System.out.println(player.getName() + " tira i dadi: Equip= "+equip + " Move= "+move);
		player.Move(total);
		
	}
	
	public int roll() {
		return diceEq.roll()+ diceMove.roll();
	}
	
}


