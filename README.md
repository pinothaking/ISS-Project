# 🎲 The Three Kingdoms (Board Game)

_Un gioco da tavolo a turni sviluppato con un'architettura ibrida:_

- **Backend (Java):** Gestisce la logica di gioco, la mappa, i turni e lo stato dei giocatori.
- **Frontend (Python/Pygame):** Gestisce la grafica, le animazioni, l'input dell'utente e il sonoro.
- **Comunicazione:** I due processi comunicano tramite **Unix Domain Sockets**.

---

## 📋 Requisiti

Per eseguire il gioco, assicurati di avere installato:

- **Sistema Operativo:** Linux o macOS (Windows richiede WSL per i Socket Unix).  
- **Java JDK:** Versione 11 o superiore.  
- **Python:** Versione 3.x.  
- **Librerie Python:** `pygame`.

---

## ⚙️ Installazione

Segui questi passaggi la prima volta che scarichi il progetto.

### 1. Configura l'ambiente Python

Apri il terminale nella cartella del progetto e crea un virtual environment:


# Crea l'ambiente virtuale chiamato 'myenv'
python3 -m venv myenv

# Attiva l'ambiente (opzionale, lo script lo usa direttamente)
source myenv/bin/activate

# Installa Pygame
pip install pygame

## ⚙️ Avvio del Gioco

### 1. Prepara lo script di avvio

Assicurati che lo script `start_game.sh` abbia i permessi di esecuzione:

```bash
chmod +x start_game.sh
