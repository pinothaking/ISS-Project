package stats_change;

public class FakeObserver implements Observer{
	public boolean update = false;
	
	@Override
	
	public void update() {
		update = true;
	}
}