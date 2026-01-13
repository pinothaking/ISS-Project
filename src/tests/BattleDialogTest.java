package battle_dialog;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BattleDialogTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUpStreams() {
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void showPrintsStartAndState() {
        BattleState state = new BattleState(50, 100);
        BattleDialog dialog = new BattleDialog();

        dialog.show(state);

        String output = outContent.toString();
        assertTrue(output.contains("Battaglia iniziata!"));
        assertTrue(output.contains("Turno 1"));
        assertTrue(output.contains("Il giocatore ha lanciato il dado"));
        assertTrue(output.contains("Il nemico ha lanciato il dado"));
    }

    @Test
    void updateStatePrintsCurrentState() {
        BattleState state = new BattleState(30, 80);
        BattleDialog dialog = new BattleDialog();

        dialog.update(state);

        String output = outContent.toString();
        assertTrue(output.contains("Turno 1"));
        assertTrue(output.contains("HP Giocatore: 80"));
        assertTrue(output.contains("HP Nemico: 30"));
    }
}