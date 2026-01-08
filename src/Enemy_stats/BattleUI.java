package Enemy_stats;
import Character.Enemy.Enemy;
public class BattleUI implements EnemyObserver {

    @Override
    public void update(Enemy enemy) {
        System.out.println("=== STATS NEMICO ===");
        System.out.println("HP: " + enemy.getHp());
        System.out.println("ATK: " + enemy.getAttack());
        System.out.println("DEF: " + enemy.getDefense());
        System.out.println("===================");
    }
}
