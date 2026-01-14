🎲 The Three Kingdoms (Board Game)

Un gioco da tavolo a turni sviluppato con un'architettura ibrida:

    Backend (Java): Gestisce la logica di gioco, la mappa, i turni e lo stato dei giocatori.

    Frontend (Python/Pygame): Gestisce la grafica, le animazioni, l'input dell'utente e il sonoro.

    Comunicazione: I due processi comunicano tramite Unix Domain Sockets.

📋 Requisiti

Per eseguire il gioco, assicurati di avere installato:

    Sistema Operativo: Linux o macOS (Windows richiede WSL per i Socket Unix).

    Java JDK: Versione 11 o superiore.

    Python: Versione 3.x.

    Librerie Python: pygame.

⚙️ Installazione

Segui questi passaggi la prima volta che scarichi il progetto.
1. Configura l'ambiente Python

Apri il terminale nella cartella del progetto e crea un virtual environment:
Bash

# Crea l'ambiente virtuale chiamato 'myenv'
python3 -m venv myenv

# Attiva l'ambiente (opzionale, lo script lo usa direttamente)
source myenv/bin/activate

# Installa Pygame
pip install pygame

2. Prepara lo script di avvio

Assicurati che lo script start_game.sh abbia i permessi di esecuzione:
Bash

chmod +x start_game.sh

🚀 Come Eseguire il Gioco

Una volta configurato, puoi avviare tutto (Server Java + Client Python) con un solo comando:
Bash

./start_game.sh

    Nota: Lo script compilerà automaticamente i file Java e avvierà entrambi i processi. Se vuoi chiudere il gioco, basta chiudere la finestra di Python.

🎮 Comandi di Gioco
Menu Iniziale

    [2], [3], [4]: Premi questi tasti sulla tastiera per selezionare il numero di giocatori.

Durante la Partita (Mappa)

    BARRA SPAZIATRICE: Lancia il dado per muoverti (solo quando è il tuo turno).

Durante il Combattimento

    BARRA SPAZIATRICE: Lancia il dado per attaccare il nemico.

    K: (Debug/Cheat) Uccide istantaneamente il nemico.

Schermata di Vittoria

    [1]: Scegli "Libera Soul" (Bonus Aura).

    [2]: Scegli "Ruba Soul" (Bonus Soul).

📂 Struttura del Progetto

ISS-Project/
├── img/                  # Contiene tutte le immagini e gli SpriteSheet
├── myenv/                # Ambiente virtuale Python
├── src/
│   ├── UnixServer.java   # Entry point del Server Java
│   ├── game_engine/      # Logica Java (Motore di gioco)
│   ├── PythonUI/         # Codice Python (UI)
│   │   ├── prova.py      # Main loop del gioco (Client)
│   │   ├── pawn.py       # Classe Pedina
│   │   ├── dice.py       # Classe Dado
│   │   └── ...
└── start_game.sh         # Script per compilare e avviare tutto

🛠 Troubleshooting (Risoluzione Problemi)

Errore: ConnectionRefusedError

    Significa che il server Java non è partito in tempo. Riprova ad avviare lo script. Se persiste, controlla che non ci siano errori di compilazione Java nel terminale.

Errore: pygame module not found

    Assicurati di aver installato pygame dentro myenv: ./myenv/bin/pip install pygame

Le pedine non si muovono

    Assicurati di aver premuto SPAZIO solo quando appare la scritta "PREMI SPAZIO" o "TOCCA A P1".

Buon divertimento su The Three Kingdoms!