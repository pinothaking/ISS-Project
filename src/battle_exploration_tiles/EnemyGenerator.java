package battle_exploration_tiles;

import Character.Enemy.Enemy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyGenerator {

    private Random random;

    public EnemyGenerator() {
        random = new Random();
    }

    public EnemyGenerator(long seed) {
        random = new Random(seed);
    }

    /**
     * Genera nemici casuali per un settore
     * @param numTiles
     * @return
     */
    public List<EnemyTile> generateEnemies(int numTiles) {
        List<EnemyTile> tiles = new ArrayList<>();

        // Logica: 1 nemico ogni 3 tile
        int numEnemies = Math.max(1, numTiles / 3);

        for (int i = 0; i < numEnemies; i++) {
            Enemy enemy = createRandomEnemy();
            EnemyTile tile = new EnemyTile(enemy);
            tiles.add(tile);
        }

        return tiles;
    }

    /**
    * Crea un nemico casuale con statistiche generate in modo random
    * @return un nuovo nemico con HP, attacco e difesa casuali
    */
    private Enemy createRandomEnemy() {
        int hp = 10 + random.nextInt(11);
        int attack = 2 + random.nextInt(5);
        int defense = 1 + random.nextInt(3);
        return new Enemy(hp, attack, defense);
    }
}