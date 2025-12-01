package battle_exploration_tiles;

public class EmptyTile implements Tile{
    @Override
    public void interact(GameContext context){
        System.out.println("Casella vuota, tutto tranquillo");

        context.setState(new ExplorationState());
    }
}
