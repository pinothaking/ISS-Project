package Player;

public class Player{

	private String name;
	private int pos;
	
	
	public Player (String name){
		
		this.name = name;
		this.pos = 0;
	}
	
	public void takeTurn() {
		
	}
	public void Move(int steps){
		
	this.pos += steps;
	
	}
	
	public String getName() {
		
		return name;
	}
	
	public int getPos() {
		return pos;
	}
}
