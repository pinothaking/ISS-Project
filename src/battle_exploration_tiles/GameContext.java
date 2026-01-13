package battle_exploration_tiles;

// Contesto di gioco

public class GameContext {
    private GameState currGameState; // salvo il contesto corrente 
    private boolean inExploration = true;

    public GameContext(){
        currGameState = new ExplorationState(); // inizialmente è esplorazione
    }

    // metodo per cambiare il contesto da battaglia a esplorazione e viceversa
    public void setState(GameState newState){
        System.out.println("Game State cambiato in: " + newState.getClass().getSimpleName()); 
    }

    // metodo per muoversi in una casella
    public void moveTo(Tile tile){
        System.out.println("Giocatore x si muove su una casella...");
        tile.interact(this);
    }

    // metodo per aggiornare lo stato di gioco corrente
    public void gameLoop(){
        currGameState.update();
    }

    public boolean isExploration(){
        return this.inExploration;
    }
}
