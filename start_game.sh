#!/bin/bash

# 1. Vai nella cartella radice del progetto
cd "$(dirname "$0")"

# 2. PULIZIA (Opzionale, per evitare conflitti)
# Rimuoviamo vecchi file class per essere sicuri di ricompilare tutto fresco
echo "🧹 Pulizia vecchi file compilati..."
find src -name "*.class" -delete

# 3. COMPILAZIONE "IN PLACE" (Dentro le cartelle sorgente)
echo "🔨 Compilazione in corso..."
# Compiliamo UnixServer e tutte le dipendenze, lasciando i .class accanto ai .java
javac -cp src src/UnixServer.java

if [ $? -eq 0 ]; then
    echo "✅ Compilazione Java riuscita."
else
    echo "❌ Errore compilazione Java. Controlla il codice."
    exit 1
fi

# 4. AVVIA Java server
echo "🚀 Avvio Server Java..."
# -cp src dice a Java: "Cerca le classi partendo dalla cartella src"
java -cp src UnixServer &

# Salviamo il PID per chiuderlo dopo
SERVER_PID=$!

# Piccola pausa per dare tempo a Java di partire prima che Python provi a connettersi
sleep 2

# 5. AVVIA Python Pygame
echo "🐍 Avvio Client Python..."
./myenv/bin/python src/PythonUI/prova.py

# 6. CHIUSURA
# Quando chiudi la finestra Python, questo comando uccide il server Java
kill $SERVER_PID
echo "👋 Gioco chiuso."