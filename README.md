# 🎲 L'Ascesa

**The Three Kingdoms** è un gioco da tavolo digitale a turni sviluppato con un'architettura ibrida Client-Server.

Il progetto combina la robustezza di **Java** per la logica di gioco e la versatilità di **Python (Pygame)** per l'interfaccia grafica, comunicando in tempo reale tramite **Unix Domain Sockets**.

---

## 🏗️ Architettura del Progetto

* **Backend (Java):** Gestisce il motore di gioco, la mappa, la gestione dei turni, lo stato dei giocatori e la logica degli eventi.
* **Frontend (Python/Pygame):** Gestisce il rendering grafico, le animazioni (sprite), l'input dell'utente e il ciclo di gioco visivo.
* **Comunicazione:** IPC (Inter-Process Communication) tramite Socket AF_UNIX.

---

## 📋 Prerequisiti

Per eseguire il gioco, assicurati di avere installato:

* **OS:** Linux o macOS (Windows richiede WSL per i Socket Unix).
* **Java JDK:** Versione 11 o superiore.
* **Python:** Versione 3.x.
* **Librerie Python:** `pygame`.

---

## ⚙️ Installazione e Configurazione

Esegui questi passaggi la prima volta che scarichi il progetto.

### 1. Configura l'ambiente Python
Apri il terminale nella root del progetto e crea un virtual environment:

```bash
# Crea l'ambiente virtuale chiamato 'myenv'
python3 -m venv myenv
```
# Attiva l'ambiente (su Linux/Mac)
```
source myenv/bin/activate
```
# Installa Pygame
```
pip install pygame
```
# Prepara lo script di avvio

Assicurati che lo script bash abbia i permessi di esecuzione:
Bash
```
chmod +x start_game.sh
```
# 🚀 Come Eseguire il Gioco

Una volta configurato l'ambiente, puoi avviare tutto (Server Java + Client Python) con un solo comando:
Bash
```
./start_game.sh
```
    Nota: Lo script compilerà automaticamente i file Java e avvierà entrambi i processi in background.

# 🎮 Comandi di Gioco
## 🏠 Menu Iniziale
```
    [2], [3], [4]: Premi questi tasti per selezionare il numero di giocatori.
```
## 🗺️ Mappa (Esplorazione)
```
    BARRA SPAZIATRICE: Lancia il dado per muovere la pedina (solo quando è il tuo turno).
```
## ⚔️ Combattimento (Battle Arena)
```
Quando incontri un nemico, il gioco passa alla modalità battaglia.

    BARRA SPAZIATRICE: Lancia il dado per attaccare il nemico.

    K: (Debug/Cheat) Sconfigge istantaneamente il nemico.
```
## 🏆 Schermata di Vittoria

Dopo aver sconfitto un nemico, scegli la ricompensa:
```
    [1]: Libera Soul (+10 Aura).

    [2]: Ruba Soul (+1 Soul).
```
