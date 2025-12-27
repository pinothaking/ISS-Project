package Roll_Dice_Player;

import java.util.Random;

public class Enemy {
    private int HP;
    // aggiungere attributo soul (che tipo ?)

    public int attack(Player player){
        System.out.println("Il nemico sta attaccando " + player.getName() + "\n");
        Random rand = new Random();
        int damage = rand.nextInt(6) + 1; // danno casuale da 1 a 6 (provvisorio)
        System.out.println(player.getName() + "ha subito " + damage + " danni!\n");
        player.setHp(Math.max(0, this.HP - damage));
        return damage;
    }
}
