package Roll_Dice_Player;

public class StatsDisplay implements PlayerDisplay {

    @Override
    public void update(Player stats) {
        System.out.println("HP: " + stats.getHp());
        System.out.println("Aura: " + stats.getAura());
        System.out.println("Spirit: " + stats.getSpirit());
    }
}
