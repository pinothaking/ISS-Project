package battle_dialog;

public class Main {
    public static void main(String[] args) {
        Player player = new Player();

        BattleState battleState = new BattleState(50, 100);
        BattleTile battleTile = new BattleTile(battleState);
        BattleDialog battleDialog = new BattleDialog();
        
        player.setCurrentTile(battleTile);
        player.enterBattleTile(battleDialog);
    }
}
