package battle_exploration_tiles;
import Character.*;
import Character.Enemy.Enemy;
import Character.Enemy.FightGuardianCmd;
import Character.Enemy.PayAndPassCmd;
import Character.Player.Player;

public class GuardianTile implements Tile{
    private Enemy guardian;
    private int costToPass; // da decidere se essere fisso per ogni guardiano

    public GuardianTile(Enemy guardian){
        this.guardian = guardian;
    }

    @Override
    public void interact(GameContext context, Player player){
        if(player.getSpirit() >= costToPass){
            System.out.println(player.getName() + " ha Spirit Points sufficienti");

            PayAndPassCmd payCommand = new PayAndPassCmd(player, costToPass, context);

            payCommand.execute();
        } else{
            System.out.println(player.getName() + " non ha Spirit Points sufficienti!");

            FightGuardianCmd fightCommand = new FightGuardianCmd(player, guardian, context);

            fightCommand.execute();
        }
    }
}
