package battle_exploration_tiles;

import Character.Enemy.Enemy;

public class EnemyTile implements Tile {

    private Enemy enemy;

    public EnemyTile() {
        // di default il nemico è null
    }

    public EnemyTile(Enemy enemy) {
        this.enemy = enemy;
    }

    // Setter e getter
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    @Override
    public void interact(GameContext context) {
        if (enemy != null) {
            System.out.println("C'è un nemico nella tua casella! Tocca combattere bro");
            context.setState(new BattleState());
        } else {
            System.out.println("Casella vuota.");
        }
    }
}
