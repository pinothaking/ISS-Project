import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import stats_change.Command;

public class InputHandlerTest {

    @Test
    void onKeyPressedTest() {        
        InputHandler handler = new InputHandler();
        TestCommand command = new TestCommand();

        handler.bind("Q", command);
        handler.onKeyPressed("Q");

        assertTrue(command.wasExecuted());
    }

    static class TestCommand implements Command {
        private boolean executed = false;

        @Override
        public void execute() {
            executed = true;
        }

        boolean wasExecuted() {
            return executed;
        }
    }
}