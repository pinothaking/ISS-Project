package battle_exploration_tiles;

import Character.Player.Player;

// Interfaccia casella

public interface Tile {
    // default per far in modo di alternare i due metodi uguali in base all'esigenza
    // default vuole un corpo (in questo caso va bene un corpo vuoto)
   default void interact(GameContext context){}; 

   default void interact(GameContext context, Player player){};
}
