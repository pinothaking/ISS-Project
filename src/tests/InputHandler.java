package tests;
import java.util.HashMap;
import java.util.Map;

import stats_change.command;

public class InputHandler {

    private Map<String, command> CommandMap = new HashMap<>();

    public void bind(String key, command command) {
        CommandMap.put(key, command);
    }

    public void onKeyPressed(String key) {
        command command = CommandMap.get(key);
        if (command != null) {
            command.execute();
        }
    }
}
