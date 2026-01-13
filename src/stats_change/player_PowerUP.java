package stats_change;

public class Player_PowerUP implements Observer {
	
	private ConcreteCommand consumeSoulConcreteCommand;
	
	public Player_PowerUP(ConcreteCommand consumeSoulConcreteCommand) {
		this.consumeSoulConcreteCommand = consumeSoulConcreteCommand;
	}
	
	public void ConsumeSoul() {
		consumeSoulConcreteCommand.execute();
	}
	
	@Override
	public void update() {
		System.out.println("Player Aggiornato");
	}

}
