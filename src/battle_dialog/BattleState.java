package battle_dialog;

public class BattleState {
    private int enemyHealth;
    private int playerHealth;
    private int Turn;

    public BattleState(int enemyHealth, int playerHealth){
        this.enemyHealth = enemyHealth;
        this.playerHealth = playerHealth;
        this.Turn = 0; // 0 per player, 1 per enemy
    }

    public int getEnemyHealth(){
        return enemyHealth;
    }

    public int getPlayerHealth(){
        return playerHealth;
    }

    public int getTurn(){
        return Turn;
    }

    public void nextTurn(){
        Turn++;
    }
}