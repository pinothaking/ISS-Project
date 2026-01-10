import java.util.HashMap;
import java.util.Map;

import stats_change.Command;

public class InputHandler {

    private Map<String, Command> CommandMap = new HashMap<>();

    public void bind(String key, Command command) {
        CommandMap.put(key, command);
    }

    public void onKeyPressed(String key) {
        Command command = CommandMap.get(key);
        if (command != null) {
            command.execute();
        }
    }
}
