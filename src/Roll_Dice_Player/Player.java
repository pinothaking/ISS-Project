package Roll_Dice_Player;

import game_map_state.GameMapTile;

public class Player {
	private int id; // ID univoco
	private String name;
	private GameMapTile pos;

	public Player(int id, String name, GameMapTile pos) {
		this.id = id;
		this.name = name;
		this.pos = pos;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public GameMapTile getPosition() {
		return pos;
	}

	public void moveTo(GameMapTile newTile) {
		this.pos.setPlayer(false);
		this.pos = newTile;
		this.pos.setPlayer(true);
	}
}
