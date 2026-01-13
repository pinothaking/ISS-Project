package tests.Enemy;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Character.Enemy.Enemy;
import Character.Enemy.Enemy_stats.BattleUI;

public class BattleUITest {

    private BattleUI battleUI;
    private Enemy enemy;
    private ByteArrayOutputStream output;
    private PrintStream originalOut;

    @BeforeEach
    void setup() {
        battleUI = new BattleUI();
        enemy = new TestEnemy(100, 20, 10);

        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void updatePrintsEnemyStats() {
        battleUI.update(enemy);

        String expected = "=== STATS NEMICO ===\n" +
                "HP: 100\n" +
                "ATK: 20\n" +
                "DEF: 10\n" +
                "===================\n";

        assertEquals(expected, output.toString());
    }

    /**
     * Enemy finto solo per i test
     */
    private static class TestEnemy extends Enemy {

        private int hp;
        private int attack;
        private int defense;

        TestEnemy(int hp, int attack, int defense) {
            this.hp = hp;
            this.attack = attack;
            this.defense = defense;
        }

        @Override
        public int getHp() {
            return hp;
        }

        @Override
        public int getAttack() {
            return attack;
        }

        @Override
        public int getDefense() {
            return defense;
        }
    }
}
