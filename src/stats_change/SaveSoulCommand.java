package stats_change;

<<<<<<< HEAD:src/stats_change/SaveSoul.java
public class SaveSoul implements Command {
=======
public class SaveSoulCommand implements command {
>>>>>>> 84b39e719abe94ec81e66f0bc62716c556ac13d1:src/stats_change/SaveSoulCommand.java

    private Receiver receiver;
    private int spiritGain;
    private int auraGain;

    public SaveSoul(Receiver receiver, int spiritGain, int auraGain) {
        this.receiver = receiver;
        this.spiritGain = spiritGain;
        this.auraGain = auraGain;
    }

    @Override
    public void execute() {
        receiver.saveSoul(spiritGain, auraGain);
    }
}
