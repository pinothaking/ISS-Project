package DiceEq;
import Dice.Dice;

import java.util.Random;

public class DiceEq extends Dice {
	private int face;
	private Random rand = new Random();
	
	public DiceEq(int face) {
		this.face = face;
	}
	
	@Override

	public int roll() {
		return rand.nextInt(face) +1 ;
	}
}
