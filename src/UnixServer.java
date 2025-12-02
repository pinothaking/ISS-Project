
import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.file.*;

public class UnixServer {
    public static void main(String[] args) throws Exception {
        Path socketPath = Path.of("/tmp/game_socket"); // Which file is being created
        Files.deleteIfExists(socketPath); // delete eventually

        UnixDomainSocketAddress address = UnixDomainSocketAddress.of(socketPath); // creating the unix address
        try (ServerSocketChannel server = ServerSocketChannel.open(StandardProtocolFamily.UNIX)) { // building the
                                                                                                   // socket of type
                                                                                                   // unix
            server.bind(address); // server listening
            System.out.println("Server (Java) avviato...");

            try (SocketChannel client = server.accept()) { // waiting for a client to accept request
                System.out.println("Python connesso.");
                /*
                 * Sending mesgs in string ( eventually we work with objs and values we need to
                 * modify )
                 * 
                 */
                BufferedReader in = new BufferedReader(new InputStreamReader(Channels.newInputStream(client)));
                PrintWriter out = new PrintWriter(Channels.newOutputStream(client), true);

                // sending msg
                out.println("DRAW_BOARD");
                out.println("WAIT_PLAYER");
                out.println("MOVE_PIECE");

                // waiting for ack
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Python ha risposto: " + line);
                }
            }
        }
    }
}