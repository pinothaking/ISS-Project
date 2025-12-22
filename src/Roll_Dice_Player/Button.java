package Roll_Dice_Player;

public class Button {
 private AbstractCommand command;
 
 public Button(AbstractCommand command) {
	 this.command = command;
 }
 
 public void onClick(){
	 if (command != null) {
		 command.execute();
	 	}
 	}
 
 public void setCommand() {
	 this.command = command;
 }
}


