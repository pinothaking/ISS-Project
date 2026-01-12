package tests.Enemy;

public class EnemyTest {

    private int hp;
    private int attack;
    private int defense;

    public EnemyTest(int hp, int attack, int defense) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
    }

    // Getters
    public int getHp() { return hp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }

    @Override
    public String toString() {
        return "EnemyTest{" +
                "hp=" + hp +
                ", attack=" + attack +
                ", defense=" + defense +
                '}';
    }
}