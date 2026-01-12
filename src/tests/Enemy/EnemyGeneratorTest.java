package tests.Enemy;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EnemyGeneratorTest {

    @Test
    void testEnemyGenerationNotNull() {
        EnemyTest enemy = EnemyFactoryTest.createEnemyForSector(1);
        assertNotNull(enemy, "Enemy non dovrebbe essere null");
    }

    @Test
    void testEnemyStatsPositive() {
        EnemyTest enemy = EnemyFactoryTest.createEnemyForSector(1);
        assertTrue(enemy.getHp() > 0, "HP deve essere positivo");
        assertTrue(enemy.getAttack() > 0, "Attack deve essere positivo");
        assertTrue(enemy.getDefense() >= 0, "Defense >= 0");
    }

    @Test
    void testEnemyScalesWithSector() {
        EnemyTest lowSector = EnemyFactoryTest.createEnemyForSector(1);
        EnemyTest highSector = EnemyFactoryTest.createEnemyForSector(5);

        assertTrue(highSector.getHp() > lowSector.getHp(), "HP cresce con il settore");
        assertTrue(highSector.getAttack() >= lowSector.getAttack(), "Attack cresce con il settore");
    }

    @Test
    void testMultipleEnemiesUniqueInstances() {
        EnemyTest e1 = EnemyFactoryTest.createEnemyForSector(1);
        EnemyTest e2 = EnemyFactoryTest.createEnemyForSector(1);
        assertNotSame(e1, e2, "Ogni chiamata deve creare un nuovo oggetto Enemy");
    }
}