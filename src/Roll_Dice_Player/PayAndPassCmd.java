package Roll_Dice_Player;
import battle_exploration_tiles.*;

// Sottoclasse di Command per pagare e passare nella cella guardiano
public class PayAndPassCmd extends Command{
    
    private Player player;
    private int cost;
    private GameContext context;

    public PayAndPassCmd(Player player, int cost, GameContext context){
        this.player = player;
        this.cost = cost;
        this.context = context;
    }

    @Override
    public void execute(){
        // sottrai i punti spirito al player
        player.setSpirit(player.getSpirit() - this.cost);
        System.out.println("Il giocatore ha pagato " + this.cost + " richiesti dal guardiano\n");

        // a questo punto il gamestate deve essere exploration
        this.context.setState(new ExplorationState());
    }   
}
