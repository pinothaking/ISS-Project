package stats_change;

public class Receiver {
	public int AuraPoint;
	private int statistica;
	
	public void addAuraPoint(int increment) {
		AuraPoint += increment;
	}
	public void addStatistica (int increment) {
		statistica += increment;
	}
	
	public int getAuraPoint() {
		return AuraPoint;
	}
	
	public int getStatistica() {
		return statistica;
	}
}
