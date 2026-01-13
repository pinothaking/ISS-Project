package battle_exploration_tiles;

import Character.Enemy.Enemy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyGenerator {

    private final Random random;

    public EnemyGenerator() {
        random = new Random();
    }

    public EnemyGenerator(long seed) {
        random = new Random(seed);
    }

    /**
     * Genera nemici casuali per un settore.
     * @param numTiles numero di tile nel settore
     * @param realm Realm corrente (INFERNO, PURGATORIO, PARADISO)
     * @return lista di EnemyTile con nemici
     */
    public List<EnemyTile> generateEnemies(int numTiles, Realm realm) {
        List<EnemyTile> tiles = new ArrayList<>();

        // 1 nemico ogni 3 tile
        int numEnemies = Math.max(1, numTiles / 3);

        for (int i = 0; i < numEnemies; i++) {
            Enemy enemy = createRandomEnemy(realm); // passiamo il Realm
            EnemyTile tile = new EnemyTile(enemy);
            tiles.add(tile);
        }

        return tiles;
    }

    /**
     * Crea un nemico casuale con statistiche generate in modo random
     * @param realm Realm della tile
     * @return un nuovo nemico con HP, attacco, difesa e tipo
     */
    private Enemy createRandomEnemy(Realm realm) {
        int hp = 10 + random.nextInt(11);      // HP: 10-20
        int attack = 2 + random.nextInt(5);    // Attacco: 2-6
        int defense = 1 + random.nextInt(3);   // Difesa: 1-3

        // Passiamo direttamente il Realm al nemico
        return new Enemy(hp, attack, defense, realm);
    }
}
