package diceMove;
import Dice.Dice;
import java.util.Random;


public class diceMove extends Dice{
	private int face;
	private Random rand = new Random();
	
	public diceMove(int face) {
		this.face = face;
	}
	@Override
	public int roll () {
		return rand.nextInt(face)+1;
	}
}

