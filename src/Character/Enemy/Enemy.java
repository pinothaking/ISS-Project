package Character.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Character.Enemy.Enemy_stats.EnemyObserver;
import Character.Player.Player;


public class Enemy {

    private int hp;
    private int attack;
    private int defense;
    private EnemyType type;

    private List<EnemyObserver> observers = new ArrayList<>();

    public Enemy(int hp, int attack, int defense, EnemyType type) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.type = type;
    }

    // Observer methods
    public void attach(EnemyObserver observer) {
        observers.add(observer);
    }

    public void detach(EnemyObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (EnemyObserver o : observers) {
            o.update(this);
        }
    }

    // Game logic
    public void takeDamage(int damage) {
        hp -= Math.max(0, damage - defense);
        notifyObservers();
    }

    // Getters (solo lettura per la View)
    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }
    
    public int attack(Player player) {
        System.out.println("Il nemico sta attaccando " + player.getName() + "\n");
        Random rand = new Random();
        int damage = rand.nextInt(6) + 1; // danno casuale da 1 a 6 (provvisorio)
        System.out.println(player.getName() + "ha subito " + damage + " danni!\n");
        player.setHp(Math.max(0, this.hp - damage));
        return damage;
    }
}