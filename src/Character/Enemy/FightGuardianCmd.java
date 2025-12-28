package Character.Enemy;
import Character.Player.Player;
import battle_exploration_tiles.*;

public class FightGuardianCmd extends Command{
    private Player player;
    private Enemy guardian;
    private GameContext context;

    public FightGuardianCmd(Player player, Enemy guardian, GameContext context){
        this.player = player;
        this.guardian = guardian;
        this.context = context;
    }

    @Override
    public void execute(){

        System.out.println("Il giocatore non ha pagato e intende combattere il guardiano\n");

        // in questo contesto si setta il battle state
        this.context.setState(new BattleState());
    }
}
