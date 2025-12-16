package stats_change;

public class ConcreteCommand implements command{
	// riceve l'oggetto Receiver che eseguirà le operazioni concrete
	
	
	private Receiver receiver; // -> riceve l'oggetto Receiver che eseguirà le operazioni concrete
	private int apIncrement;  // -> incremento dei punti aura da applicare al receiver
	private int staIncrement; // -> incremento delle statistiche da applicare al receiver
	
	//Costruttore: inizializza il comando con receiver e valori di incremento
	public ConcreteCommand(Receiver receiver, int apIncrement, int staIncrement) {
		this.receiver = receiver; 
		this.apIncrement = apIncrement;
		this.staIncrement = staIncrement;
	}
	
	//esegue il comando: applica gli incrementi al receiver
	@Override
	public void execute() {
		receiver.addAuraPoint(apIncrement); // -> aggiunge punti aura
		receiver.addStatistica(staIncrement); // -> aggiunge statistiche
	}
}
