import socket
import pygame
from pawn import Pawn

pygame.init()
background_colour = (234, 212, 252)
screen = pygame.display.set_mode((620, 760))
pygame.display.set_caption('The Three Kingdoms')
clock = pygame.time.Clock()

Map = None
pawn = Pawn("img/Pawn.png", 367, 700, speed=0.5)

SOCKET_PATH = "/tmp/game_socket"
s = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)
s.connect(SOCKET_PATH)
s.setblocking(False)

running = True
while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    
    try:
        data = s.recv(1024)
        if data:
            # Splitta per newline per gestire comandi multipli
            commands = data.decode().strip().split('\n')
            
            for cmd in commands:
                cmd = cmd.strip()  # Rimuovi spazi
                print(f"Comando da Java: '{cmd}'")
                
                if cmd == "DRAW_BOARD":
                    Map = pygame.image.load("img/TheThreeReign.png")
                    Map = pygame.transform.scale(Map, (620, 760))
                    
                elif cmd == "MOVE_PIECE":
                    print("oooo - Muovo pedina!")
                    pawn.move_to(297, 700)
                    pawn.scale_image((40, 40))
                    
                elif cmd == "WAIT_PLAYER":
                    print("→ Mostro messaggio: attendi il giocatore...")
            
            # Rispondi UNA volta dopo aver processato tutti i comandi
            try:
                s.sendall(b"OK\n")
            except OSError:
                print("Errore nell'invio della risposta sulla socket")
            
    except BlockingIOError:
        pass
    except (ConnectionResetError, OSError) as e:
        print("Errore socket:", e)
        running = False

    # aggiorna e disegna
    pawn.update()

    screen.fill(background_colour)
    if Map:
        screen.blit(Map, (0, 0))
    pawn.draw(screen)   
    pygame.display.flip()
    clock.tick(60)

pygame.quit()