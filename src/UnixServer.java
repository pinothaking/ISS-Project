import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.file.*;

public class UnixServer {
    public static void main(String[] args) throws Exception {
        Path socketPath = Path.of("/tmp/game_socket");
        Files.deleteIfExists(socketPath);
        UnixDomainSocketAddress address = UnixDomainSocketAddress.of(socketPath);

        try (ServerSocketChannel server = ServerSocketChannel.open(StandardProtocolFamily.UNIX)) {
            server.bind(address);
            System.out.println("Server (Java) avviato...");

            try (SocketChannel client = server.accept()) {
                System.out.println("Python connesso.");

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(Channels.newInputStream(client)));
                PrintWriter out = new PrintWriter(
                        Channels.newOutputStream(client), true);

                // Chiedi il numero di giocatori
                out.println("REQUEST_PLAYERS");
                System.out.println("→ Inviato REQUEST_PLAYERS");

                String line;
                int numPlayers = 0;
                int currentPlayer = 1;
                boolean gameStarted = false;

                while ((line = in.readLine()) != null) {
                    System.out.println("Python ha risposto: " + line);

                    if (line.startsWith("PLAYERS:")) {
                        numPlayers = Integer.parseInt(line.split(":")[1]);
                        System.out.println("Numero giocatori ricevuto: " + numPlayers);

                        // Invia i comandi iniziali
                        out.println("DRAW_BOARD");
                        out.flush();  
                        System.out.println("→ Inviato DRAW_BOARD");

                        out.println("SET_PLAYERS:" + numPlayers);
                        out.flush();  
                        System.out.println("→ Inviato SET_PLAYERS:" + numPlayers);

                        out.println("SET_TURN:" + currentPlayer);
                        out.flush();  
                        System.out.println("→ Inviato SET_TURN:" + currentPlayer);

                        out.println("WAIT_PLAYER");
                        out.flush();  
                        System.out.println("→ Inviato WAIT_PLAYER");

                        gameStarted = true;

                    } else if (line.equals("MOVE_DONE")) {
                        // Il giocatore ha completato la mossa
                        System.out.println("✓ Mossa completata dal giocatore " + currentPlayer);

                        // Passa al prossimo giocatore
                        currentPlayer++;
                        if (currentPlayer > numPlayers) {
                            currentPlayer = 1; // Ricomincia dal primo
                        }

                        // Invia il nuovo turno
                        System.out.println("→ Cambio turno, ora è il turno di: " + currentPlayer);
                        out.println("SET_TURN:" + currentPlayer);
                        out.println("MOVE_PIECE");
                        out.println("WAIT_PLAYER"); // ← QUI: Aspetta che il nuovo giocatore faccia la mossa
                        System.out.println("→ Inviato WAIT_PLAYER per giocatore " + currentPlayer);

                    } else if (line.equals("OK")) {
                        // Comando ricevuto ed eseguito (ACK)
                    }
                }
            }
        }
    }
}```
/*
 * 
 * 
 * 
 * ##Spiegazione:
 * 
 * Il comando`WAIT_PLAYER`
 * viene inviato in**due momenti**:
 * 
 * 1.**All'inizio del gioco**-
 * dopo aver
 * impostato il
 * turno del
 * primo giocatore 2.**
 * Dopo ogni
 * cambio turno**-
 * quando un
 * giocatore completa
 * la sua
 * mossa e
 * passa al successivo
 * 
 * ##
 * Sequenza completa:```Java→Python:
 * REQUEST_PLAYERS Python→Java:PLAYERS:2 Java→Python:
 * DRAW_BOARD
 * Java→Python:SET_PLAYERS:2 Java→Python:SET_TURN:1←
 * È il
 * turno del giocatore 1 Java→Python:WAIT_PLAYER←
 * Aspetta che
 * faccia la mossa[Giocatore 1
 * preme SPAZIO]Python→Java:
 * MOVE_DONE Java→Python:SET_TURN:2←
 * Ora è
 * il turno
 * del giocatore 2 Java→Python:
 * MOVE_PIECE
 * Java→Python:WAIT_PLAYER←
 * Aspetta che
 * faccia la mossa[Giocatore 2
 * preme SPAZIO]Python→Java:
 * MOVE_DONE Java→Python:SET_TURN:1←
 * Torna al giocatore 1 Java→Python:
 * MOVE_PIECE
 * Java→Python:WAIT_PLAYER←
 * E così via
 */