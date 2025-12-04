package Roll_Dice_Player;
import game_map_state.GameMapTile;

public class Player{

	private String name;
	private GameMapTile pos;
	
	
	public Player (String name , GameMapTile pos){
		
		this.name = name;
		this.pos = pos;
	}
	
	public void takeTurn() {
		return;
	}
	
	public String getName() {
		return name;
	}
	
	public GameMapTile getPosition() {
		return pos;
	}

	public void moveTo(GameMapTile newTile) {
        this.pos.setPlayer(false); // rimuove dalla tile precedente
        this.pos = newTile;
        this.pos.setPlayer(true);  // segna la nuova tile
    }
}
