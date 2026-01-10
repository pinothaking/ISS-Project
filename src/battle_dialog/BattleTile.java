package battle_dialog;

public class BattleTile extends Tile{

    private BattleState battleState;

    public BattleTile(BattleState battleState){
        this.battleState = battleState;
    }
    @Override
    public boolean isBattleTile(){
        return true;
    }
    public BattleState getBattleState(){
        return this.battleState;
    }
}
