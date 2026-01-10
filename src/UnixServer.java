import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.util.*;
import java.lang.String;

// Assicurati che questi package esistano nel tuo progetto
import game_engine.GameEngine;
import game_map_state.*;
import battle_exploration_tiles.*;
import Character.Player.*;

public class UnixServer {
    public static void main(String[] args) throws Exception {
        // Configurazione Socket
        Path socketPath = Path.of("/tmp/game_socket");
        Files.deleteIfExists(socketPath);
        UnixDomainSocketAddress address = UnixDomainSocketAddress.of(socketPath);

        // Inizializzazione logica
        MapFactory mapFactory = new MapFactory();
        GameMap gameMap = null;
        GameEngine engine = new GameEngine();
        List<Player> players = new ArrayList<>();

        try (ServerSocketChannel server = ServerSocketChannel.open(StandardProtocolFamily.UNIX)) {
            server.bind(address);
            System.out.println("Server (Java) avviato... In attesa di Python.");

            try (SocketChannel client = server.accept()) {
                System.out.println("Python connesso.");

                BufferedReader in = new BufferedReader(new InputStreamReader(Channels.newInputStream(client)));
                PrintWriter out = new PrintWriter(Channels.newOutputStream(client), true);

                // Step 1: Chiedo quanti giocatori
                out.println("REQUEST_PLAYERS");
                System.out.println("→ Inviato REQUEST_PLAYERS");

                String line;
                int numPlayers = 0;
                int currentPlayerIndex = 0; // Indice della lista (0, 1, 2...)

                while ((line = in.readLine()) != null) {
                    // Debug leggero per vedere cosa arriva
                    if (!line.equals("OK"))
                        System.out.println("Python dice: " + line);

                    // --- SETUP INIZIALE ---
                    if (line.startsWith("PLAYERS:")) {
                        numPlayers = Integer.parseInt(line.split(":")[1]);
                        System.out.println("Numero giocatori ricevuto: " + numPlayers);

                        // Creazione Mappa
                        gameMap = mapFactory.createMap();
                        System.out.println("Mappa generata.");

                        // Creazione Giocatori sulla casella iniziale (Settore 0, Tile 0)
                        GameMapTile startTile = gameMap.getSectors().get(0).getTiles().get(0);

                        players.clear(); // Pulizia sicurezza
                        for (int i = 0; i < numPlayers; i++) {
                            // Nota: Passo 'i' come ID e 'startTile' come posizione
                            Player p = new Player(i, "Player " + (i + 1), startTile, 100, 0, 0);
                            p.moveTo(startTile);
                            players.add(p);
                        }
                        System.out.println("Creati " + numPlayers + " giocatori.");

                        // Invio configurazione alla UI
                        out.println("DRAW_BOARD");
                        out.println("SET_PLAYERS:" + numPlayers);

                        // Dico che tocca al Player 1 (Indice 0 + 1)
                        out.println("SET_TURN:" + (currentPlayerIndex + 1));
                        out.println("WAIT_PLAYER"); // Sblocca i controlli UI

                    }

                    // --- GESTIONE DADO E MOVIMENTO ---
                    else if (line.startsWith("DICE_RESULT")) { 
                        int diceValue = Integer.parseInt(line.split(":")[1]);

                        // 1. Identifico chi si sta muovendo
                        Player currentPlayerObj = players.get(currentPlayerIndex);
                        System.out.println(
                                "Engine: Muovo " + currentPlayerObj.getName() + " di " + diceValue + " passi.");

                        // 2. L'Engine calcola la nuova posizione logica
                        engine.movePlayer(currentPlayerObj, diceValue, gameMap);

                        // 3. Recupero il nuovo ID della casella
                        GameMapTile newTile = currentPlayerObj.getPosition();
                        int newPosId = newTile.getId();

                        // 4. Dico a Python di spostare la pedina grafica
                        out.println("MOVE_PAWN_TO:" + newPosId);

                        // 5. Controllo EVENTI sulla casella
                        Tile behavior = newTile.getTileBehavior();

                        if (behavior instanceof EnemyTile) {
                            System.out.println("Java: Evento NEMICO");
                            out.println("EVENT:ENEMY");
                        } //else if (behavior instanceof ShopTile) {
                            //System.out.println("Java: Evento NEGOZIO");
                           // out.println("EVENT:SHOP");
                        else {
                            System.out.println("Java: Casella vuota");
                            out.println("EVENT:EMPTY"); // Corretto da EVENT: SHOP
                        }
                        out.flush();

                    }

                    // --- GESTIONE FINE TURNO ---
                    else if (line.equals("MOVE_DONE")) {
                        System.out.println("✓ Mossa finita per Player " + (currentPlayerIndex + 1));

                        // 1. Calcolo il prossimo giocatore (Round Robin: 0->1->2->0...)
                        currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;

                        // 2. Comunico il cambio turno
                        System.out.println("→ Ora tocca a Player: " + (currentPlayerIndex + 1));
                        out.println("SET_TURN:" + (currentPlayerIndex + 1));

                        // 3. Dico alla UI di aspettare l'input (SPAZIO)
                        out.println("WAIT_PLAYER");
                        out.flush();

                    } else if (line.equals("OK")) {
                        // Ack silenzioso
                    }
                }
            }
        }
    }
}