package stats_change;

public class SaveSoulCommand implements command {

    private Receiver receiver;
    private int spiritGain;
    private int auraGain;

    public SaveSoulCommand (Receiver receiver, int spiritGain, int auraGain) {
        this.receiver = receiver;
        this.spiritGain = spiritGain;
        this.auraGain = auraGain;
    }

    @Override
    public void execute() {
        receiver.saveSoul(spiritGain, auraGain);
    }
}
