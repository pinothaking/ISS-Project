package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import stats_change.command;

public class InputHandlerTest {

    @Test
    void onKeyPressedTest() {        
        InputHandler handler = new InputHandler();
        TestCommand command = new TestCommand();

        handler.bind("Q", command);
        handler.onKeyPressed("Q");

        assertTrue(command.wasExecuted());
    }

    static class TestCommand implements command {
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