package battle_dialog;

public class player {
    private int auraPoints;
    private Tile currentTile;

    public void setCurrentTile(Tile tile){
        this.currentTile = tile;
    }
    public Tile getCurrentTile(){
        return this.currentTile;
    }

    public void enterBattleTile(BattleDialog dialog){
        if(currentTile != null && currentTile.isBattleTile()){
            BattleTile bt = (BattleTile) currentTile;
            dialog.show(bt.getBattleState());
        }else{
            System.out.println("Non sei in una Battle Tile!");
        }
    }
}
