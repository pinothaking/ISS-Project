package Character.Player;

import stats_change.Command;

public class Button {
    private Command command;

    public Button(Command command) {
        this.command = command;
    }

    public void onClick() {
        if (command != null) {
            command.execute();
        }
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}