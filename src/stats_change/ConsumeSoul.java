package stats_change;

import java.util.ArrayList;
import java.util.List;

// per fare i test
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




public class ConsumeSoul implements command {
	List<observer> observer = new ArrayList<>();
	private Receiver AuraPoint;
	
	public void attach(observer o) { // serve per aggiungere i valori/equip
		observer.add(o);
	} 
	public void detach(observer o) { // serve per togliere i valori al player/equip 
		observer.remove(o);
	}
	
	public void notifyObserver() { // per far sapere all'observer che i valori sono cambiati, 
		for(observer o : observer) { //cosi da poterli aggiornare
			o.update();
		}
	}
	
	@Override
	public void execute() {
		notifyObserver();
	}	
	
	
	//Test
	public class ConsumeSoulTest{
		private ConsumeSoul consumeSoul;
		private FakeObserver fakeObserver;
		
		@BeforeEach
		void setUp() {
			consumeSoul = new ConsumeSoul();
			fakeObserver = new FakeObserver();
		}
		
		@Test
		void notifyObserver(){
			
			consumeSoul.attach(fakeObserver);
			consumeSoul.execute();
			
			assertTrue(fakeObserver.update, "observer non notificato");
			
		}
		
		@Test
		void testAttachDetach() {
			consumeSoul.attach(fakeObserver);
			assertEquals(1, consumeSoul.observer.size());	
			
			consumeSoul.detach(fakeObserver);
			assertEquals(0, consumeSoul.observer.size());
		}
	}
	//fine test
}




