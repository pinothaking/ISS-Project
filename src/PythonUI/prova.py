import socket
import pygame
from pawn import Pawn

pygame.init()
background_colour = (234, 212, 252)
screen = pygame.display.set_mode((620, 760))
pygame.display.set_caption('The Three Kingdoms')
clock = pygame.time.Clock()
myfont = pygame.font.SysFont("lucidasanstypewriter", 35)

label = None
label_start = None
Map = None
pawn = Pawn("img/Pawn.png", 367, 700, speed=0.5)
num_players = 0
player_selection_done = False

SOCKET_PATH = "/tmp/game_socket"
s = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)
s.connect(SOCKET_PATH)
s.setblocking(False)

running = True
while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
        
        # Selezione numero giocatori con tasti numerici
        if event.type == pygame.KEYDOWN and not player_selection_done:
            if event.key == pygame.K_2:
                num_players = 2
                player_selection_done = True
                label_start = None
                # Invia a Java
                try:
                    s.sendall(f"PLAYERS:{num_players}\n".encode())
                    print(f"Inviato numero giocatori: {num_players}")
                except OSError as e:
                    print("Errore invio:", e)
            elif event.key == pygame.K_3:
                num_players = 3
                player_selection_done = True
                label_start = None
                try:
                    s.sendall(f"PLAYERS:{num_players}\n".encode())
                    print(f"Inviato numero giocatori: {num_players}")
                except OSError as e:
                    print("Errore invio:", e)
            elif event.key == pygame.K_4:
                num_players = 4
                player_selection_done = True
                label_start = None
                try:
                    s.sendall(f"PLAYERS:{num_players}\n".encode())
                    print(f"Inviato numero giocatori: {num_players}")
                except OSError as e:
                    print("Errore invio:", e)
    
    try:
        data = s.recv(1024)
        if data:
            commands = data.decode().strip().split('\n')
            for cmd in commands:
                cmd = cmd.strip()
                print(f"Comando da Java: '{cmd}'")
                
                if cmd == "REQUEST_PLAYERS":
                    print("Java chiede il numero di giocatori")
                    # Mostra prompt all'utente
                    label_start = myfont.render("Premi 2, 3 o 4 per scegliere", 1, (0, 0, 0))
                
                elif cmd == "DRAW_BOARD":
                    Map = pygame.image.load("img/TheThreeReign.png")
                    Map = pygame.transform.scale(Map, (620, 760))
                
                elif cmd.startswith("SET_PLAYERS:"):
                    num_players = int(cmd.split(":")[1])
                    label = myfont.render(f"N Player: {num_players}", 1, (255, 255, 255))
                    print(f"Numero giocatori impostato: {num_players}")
                
                elif cmd == "MOVE_PIECE":
                    print("Muovo pedina!")
                    pawn.move_to(297, 700)
                    pawn.scale_image((40, 40))
                
                elif cmd == "WAIT_PLAYER":
                    print("Attendi il giocatore...")
            
            # Rispondi dopo aver processato i comandi
            try:
                s.sendall(b"OK\n")
            except OSError:
                print("Errore nell'invio della risposta")
    
    except BlockingIOError:
        pass
    except (ConnectionResetError, OSError) as e:
        print("Errore socket:", e)
        running = False
    
    # Aggiorna e disegna
    pawn.update()
    screen.fill(background_colour)
    
    if Map:
        screen.blit(Map, (0, 0))
    
    pawn.draw(screen)
    
    if label:
        screen.blit(label, (0, 300))
    if label_start:
        screen.blit(label_start , (160 , 420))
    
    pygame.display.flip()
    clock.tick(60)

pygame.quit()
s.close()