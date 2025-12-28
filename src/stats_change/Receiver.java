package stats_change;

import Character.Player.Player;

public class Receiver {

    private Player player;
    private int statistica;

    public Receiver(Player player) {
        this.player = player;
    }

    // modifica spirit e aura del player
    public void saveSoul(int spiritGain, int auraGain) {
        player.setSpirit(player.getSpirit() + spiritGain);
        player.setAura(player.getAura() + auraGain);
    }

    // modifica statistica interna del receiver
    public void addStatistica(int increment) {
        statistica += increment;
    }

    public int getStatistica() {
        return statistica;
    }
}
