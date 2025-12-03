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

                // Attendi la risposta
                String line;
                int numPlayers = 0;
                while ((line = in.readLine()) != null) {
                    System.out.println("Python ha risposto: " + line);

                    if (line.startsWith("PLAYERS:")) {
                        numPlayers = Integer.parseInt(line.split(":")[1]);
                        System.out.println("Numero giocatori ricevuto: " + numPlayers);

                        // Ora invia i comandi di gioco
                        out.println("DRAW_BOARD");
                        out.println("SET_PLAYERS:" + numPlayers);
                        out.println("WAIT_PLAYER");
                    } else if (line.equals("OK")) {
                        System.out.println("Comando ricevuto ed eseguito");
                    }
                }
            }
        }
    }
}