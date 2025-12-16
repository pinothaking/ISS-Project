package stats_change;

public class FakeObserver implements observer{
	public boolean update = false;
	
	@Override
	
	public void update() {
		update = true;
	}
}