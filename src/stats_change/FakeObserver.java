package Power_Up_Souls;

public class FakeObserver implements observer{
	public boolean update = false;
	
	@Override
	
	public void update() {
		update = true;
	}
}