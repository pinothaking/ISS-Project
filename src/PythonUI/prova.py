import socket

SOCKET_PATH = "/tmp/game_socket"

with socket.socket(socket.AF_UNIX, socket.SOCK_STREAM) as s:
    s.connect(SOCKET_PATH)
    print("Connesso al server Java")

    while True:
        data = s.recv(1024)
        if not data:
            break

        cmd = data.decode().strip()
        print(f"Comando da Java: {cmd}")

        if cmd.startswith("DRAW_BOARD"):
            print("→ Disegno il tabellone")
        elif cmd.startswith("MOVE_PIECE"):
            print("→ Muovo la pedina:", cmd.split()[1:])
        elif cmd.startswith("WAIT_PLAYER"):
            print("→ Mostro messaggio: attendi il giocatore...")

        s.sendall(b"OK\n")
