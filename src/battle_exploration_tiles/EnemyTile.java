package battle_exploration_tiles;

public class EnemyTile implements Tile{
    @Override
    public void interact(GameContext context){
        System.out.println("C'è un nemico nella tua casella! Tocca combattere bro");

        context.setState(new BattleState());
    }
}
