package stats_change;

public class player_PowerUP implements observer {
	
	private ConcreteCommand consumeSoulConcreteCommand;
	
	public player_PowerUP(ConcreteCommand consumeSoulConcreteCommand) {
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
