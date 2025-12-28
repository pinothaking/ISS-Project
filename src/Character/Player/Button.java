package Character.Player;

import Character.Enemy.Command;

public class Button {
 private Command command;
 
 public Button(Command command) {
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


