import socket
import pygame
from pawn import Pawn

pygame.init()
background_colour = (234, 212, 252)
screen = pygame.display.set_mode((620, 760))
pygame.display.set_caption('The Three Kingdoms')
clock = pygame.time.Clock()
myfont = pygame.font.SysFont("lucidasanstypewriter", 35)
myfont_big = pygame.font.SysFont("lucidasanstypewriter", 45)

label = None
label_start = None
label_turn = None
Map = None
pawn = Pawn("img/Pawn.png", 367, 700, 1 ,speed=0.5)
num_players = 0
current_player = 1
player_selection_done = False
can_move = False

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
        
        # Gestione della mossa (esempio: premendo SPAZIO)
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_SPACE:
                print(f"SPAZIO premuto! can_move={can_move}, current_player={current_player}")
                if can_move:
                    print(f"✓ Giocatore {current_player} fa la mossa!")
                    can_move = False
                    # Notifica Java che la mossa è completata
                    try:
                        s.sendall(b"MOVE_DONE\n")
                        print("→ Inviato MOVE_DONE a Java")
                        label_turn = myfont_big.render("Attendere...", 1, (255, 100, 100))
                    except OSError as e:
                        print("Errore invio:", e)
                else:
                    print("✗ Non puoi muovere ora (can_move=False)")
    
    try:
        data = s.recv(1024)
        if data:
            commands = data.decode().strip().split('\n')
            for cmd in commands:
                cmd = cmd.strip()
                print(f"Comando da Java: '{cmd}'")
                
                if cmd == "REQUEST_PLAYERS":
                    print("Java chiede il numero di giocatori")
                    label_start = myfont.render("Premi 2, 3 o 4 per scegliere", 1, (0, 0, 0))
                
                elif cmd == "DRAW_BOARD":
                    Map = pygame.image.load("img/TheThreeReign.png")
                    Map = pygame.transform.scale(Map, (620, 760))
                
                elif cmd.startswith("SET_PLAYERS:"):
                    num_players = int(cmd.split(":")[1])
                    label = myfont.render(f"N Player: {num_players}", 1, (255, 255, 255))
                    label_start = None
                    print(f"Numero giocatori impostato: {num_players}")
                
                elif cmd.startswith("SET_TURN:"):
                    current_player = int(cmd.split(":")[1])
                    label_turn = myfont_big.render(f"Turno di Player {current_player}", 1, (255, 215, 0))
                    can_move = True
                    print(f"✓✓✓ È il turno del giocatore {current_player} - can_move={can_move}")
                
                elif cmd == "MOVE_PIECE":
                    print("→ Eseguo MOVE_PIECE")
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
        screen.blit(label, (0, 400))
    
    if label_start:
        screen.blit(label_start, (160, 360))
    
    # Mostra il turno in alto al centro
    if label_turn:
        screen.blit(label_turn, (0 , 420))
    
    # Mostra istruzioni se è il turno del giocatore
    if can_move:
        instruction = myfont.render("Premi SPAZIO per dado", 1, (255, 255, 255))
        screen.blit(instruction, (0 , 440))
    
    pygame.display.flip()
    clock.tick(60)

pygame.quit()
s.close()