package tests.Enemy;

import java.util.Random;

public class EnemyFactoryTest {

    private static final Random rand = new Random();

    // Metodo per generare nemico in base al settore
    public static EnemyTest createEnemyForSector(int sectorLevel) {
        int hp = 10 + rand.nextInt(5) + sectorLevel * 2;
        int attack = 2 + rand.nextInt(3) + sectorLevel;
        int defense = 1 + rand.nextInt(2);

        return new EnemyTest(hp, attack, defense);
    }
}