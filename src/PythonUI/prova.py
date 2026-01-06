import socket
import pygame
import math  # Serve per calcolare la distanza
from enemy import Enemy
from SpriteAnimation import SpriteAnimation
from pawn import Pawn
from dice import Dice_Move

# ------------------ CONFIGURAZIONE MAPPA ------------------
TILE_COORDINATES = {
    0: (354, 729),  # START 
    1: (293, 727),  
    2: (236, 712),
    3: (171, 699),
    4: (183, 663),
    5: (238, 655),
    6: (289, 648),
    7: (343, 653),
    8: (500, 450),
    9: (450, 450),
    10: (400, 450), # Fine primo settore?
    # ... continua fino alla fine della mappa (es. 29) ...
    11: (350, 450),
    12: (300, 450),
    # Aggiungi qui le altre coordinate fino alla fine...
}

# ------------------ INIT ------------------
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
label_event = None # Nuova label per gli eventi (Nemici/Shop)
Map = None

# Posizione iniziale (Casella 0)
start_x, start_y = TILE_COORDINATES.get(0, (0,0))
pawn = Pawn("img/Pawn.png", start_x, start_y, 1, speed=3) # Speed aumentata per test
pawn.scale_image((40, 40))

num_players = 0
current_player = 1
player_selection_done = False
can_move = False

# ------------------ STATO DI GIOCO ------------------
current_tile_id = 0       # Dove si trova logicamente la pedina ora
movement_queue = []       # Lista di coordinate (x,y) da visitare in sequenza
pending_event = None      # Evento in attesa (es. "ENEMY") da mostrare a fine movimento

# ------------------ DICE ------------------
dice = Dice_Move("img/Dice-Sheet.png", pos=(300, 200))
dice_result_sent = False

# ------------------ Stats ------------------
soul_animation = SpriteAnimation("img/WhiteFlame.png", 0, 20, 32, 32, scale=3, speed=50)
aura = SpriteAnimation("img/AuraPoints-Sheet.png", 0, 120, 32, 32, scale=3, speed=50)

#----Enemy-----–#
current_enemy = None

# ------------------ SOCKET ------------------
SOCKET_PATH = "/tmp/game_socket"
s = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)
try:
    s.connect(SOCKET_PATH)
    s.setblocking(False)
except FileNotFoundError:
    print("ERRORE: Il server Java non è avviato o il socket non esiste.")
    exit()

# ------------------ FUNZIONI DI UTILITÀ ------------------
def move_towards(pawn_obj, target_x, target_y, speed):
    """ Muove la pedina verso il target. Ritorna True se arrivato. """
    dx = target_x - pawn_obj.x
    dy = target_y - pawn_obj.y
    distance = math.sqrt(dx**2 + dy**2)

    if distance < speed:
        pawn_obj.x = target_x
        pawn_obj.y = target_y
        return True
    else:
        # Normalizza e muovi
        ratio = speed / distance
        pawn_obj.x += dx * ratio
        pawn_obj.y += dy * ratio
        return False

# ------------------ MAIN LOOP ------------------
running = True
while running:

    # -------- EVENTS --------
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

        # Selezione numero giocatori
        if event.type == pygame.KEYDOWN and not player_selection_done:
            if event.key in (pygame.K_2, pygame.K_3, pygame.K_4):
                num_players = int(event.unicode)
                player_selection_done = True
                label_start = None
                s.sendall(f"PLAYERS:{num_players}\n".encode())

        # SPAZIO = LANCIO DADO (Solo se abilitato e non ci stiamo muovendo)
        if event.type == pygame.KEYDOWN and event.key == pygame.K_SPACE:
            # Blocca il dado se la pedina si sta muovendo o se c'è un evento aperto
            if can_move and dice.get_number() is None and len(movement_queue) == 0 and pending_event is None:
                print("🎲 Lancio dado")
                can_move = False
                dice.throw(2)
                dice_result_sent = False
                label_event = None # Pulisci vecchi messaggi evento

    # -------- SOCKET RECEIVE --------
    try:
        data = s.recv(4096) # Buffer aumentato per sicurezza
        if data:
            # Gestione messaggi multipli incollati
            commands = data.decode().strip().split("\n")
            for cmd in commands:
                if not cmd: continue 
                
                print(f"Da Java: {cmd}")

                if cmd == "REQUEST_PLAYERS":
                    label_start = myfont.render("Premi 2, 3 o 4 per scegliere", 1, (0, 0, 0))

                elif cmd == "DRAW_BOARD":
                    try:
                        Map = pygame.image.load("img/TheThreeReign.png")
                        Map = pygame.transform.scale(Map, (620, 760))
                    except:
                        print("Err: Immagine mappa non trovata")

                elif cmd.startswith("SET_PLAYERS:"):
                    num_players = int(cmd.split(":")[1])
                    label = myfont.render(f"N Player: {num_players}", 1, (255, 255, 255))
                    label_start = None

                elif cmd.startswith("SET_TURN:"):
                    current_player = int(cmd.split(":")[1])
                    label_turn = myfont_big.render(f"Turno di Player {current_player}", 1, (255, 215, 0))
                    can_move = True
                
                elif cmd.startswith("MOVE_PAWN_TO:"):
                    # LOGICA DI MOVIMENTO SEQUENZIALE
                    target_id = int(cmd.split(":")[1])
                    
                    # Calcola il percorso passo dopo passo
                    # Se sono a 0 e devo andare a 4, aggiungo 1, 2, 3, 4 alla coda
                    start_range = current_tile_id + 1
                    end_range = target_id + 1
                    
                    # Gestione caso "giro della mappa" o movimento indietro se necessario
                    if target_id < current_tile_id: 
                         # Per semplicità ora gestiamo solo movimento in avanti, 
                         # o reset se necessario. Qui resetto solo il target.
                         start_range = target_id 
                         end_range = target_id + 1

                    for i in range(start_range, end_range):
                        if i in TILE_COORDINATES:
                            movement_queue.append(TILE_COORDINATES[i])
                    
                    current_tile_id = target_id # Aggiorno la posizione logica finale
                
                elif cmd.startswith("EVENT:"):
                    # Non mostro subito l'evento, lo salvo per dopo l'animazione
                    pending_event = cmd
                    print(f"Evento {cmd} in attesa fine movimento...")

                elif cmd == "THROWDICE":
                    dice.throw(2)
                    dice_result_sent = False

                elif cmd == "WAIT_PLAYER":
                    can_move = True
                    if current_player == 1: # O controlla il tuo ID locale se implementerai multiplayer online
                        label_turn = myfont_big.render("Tocca a te! Premi SPAZIO", 1, (0, 255, 0))
                    else:
                        label_turn = myfont_big.render(f"Turno Player {current_player}", 1, (255, 215, 0))

            # Non mandiamo OK qui per non intasare, Java aspetta input specifici
            # s.sendall(b"OK\n") 

    except BlockingIOError:
        pass
    except OSError:
        running = False

    # -------- LOGICA AGGIORNAMENTO ANIMAZIONI --------
    pawn.update()
    # 1. Movimento Pedina Passo-Passo
    if len(movement_queue) > 0:
        target_x, target_y = movement_queue[0]
        arrived = move_towards(pawn, target_x, target_y, speed=5) # Speed regola la velocità pixel
        
        if arrived:
            movement_queue.pop(0) # Rimuovi la tappa raggiunta
            
    # 2. Controllo Eventi (Solo se la coda movimenti è finita)
    elif len(movement_queue) == 0 and pending_event is not None:
        
        # Gestiamo l'evento che era rimasto in sospeso
        event_type = pending_event
        pending_event = None # Resetta per non rieseguirlo
        
        if event_type == "EVENT:ENEMY":
            print("Visualizza Nemico")
            label_event = myfont_big.render("COMBATTIMENTO!", 1, (255, 0, 0))
            current_enemy = Enemy("Goblin Guerriero", 100, "img/KingMorlott.png", 250, 200, scale=(450, 450))
            # Qui potresti bloccare il gioco finché l'utente non preme qualcosa<<
            # Per ora simuliamo che finisce subito:
            # In un gioco vero non manderesti MOVE_DONE subito qui, ma dopo il click
            # s.sendall(b"MOVE_DONE\n") 
            
        elif event_type == "EVENT:SHOP":
            print("Visualizza Shop")
            label_event = myfont_big.render("NEGOZIO!", 1, (0, 255, 0))
            s.sendall(b"MOVE_DONE\n")
            
        elif event_type == "EVENT:EMPTY":
            print("Nessun evento")
            label_event = None
            s.sendall(b"MOVE_DONE\n")
    
    # -------- UPDATE OBJECTS --------

    dice.update()

    soul_animation.play()
    aura.play()

    # 🎲 INVIO RISULTATO DADO
    dice_number = dice.get_number()
    if dice_number is not None and not dice_result_sent:
        print(f"🎲 Dado uscito: {dice_number}")
        s.sendall(f"DICE_RESULT:{dice_number}\n".encode())
        dice_result_sent = True

    # -------- DRAW --------
    screen.fill(background_colour)

    if Map:
        screen.blit(Map, (0, 0))

    # Importante: Disegna la pedina alle coordinate x,y aggiornate
    soul_animation.draw(screen)
    aura.draw(screen)
    pawn.draw(screen) 
    dice.draw(screen)
    if current_enemy is not None:
        current_enemy.draw(screen)

    if label: screen.blit(label, (0, 400))
    if label_start: screen.blit(label_start, (120, 360))
    if label_turn: screen.blit(label_turn, (0, 420))
    
    
    if label_event: screen.blit(label_event, (150, 300))

    if can_move and dice.get_number() is None and not movement_queue and not pending_event:
        instruction = myfont.render("Premi SPAZIO per lanciare", 1, (255, 255, 255))
        screen.blit(instruction, (0, 460))

    pygame.display.flip()
    clock.tick(60)

# ------------------ CLEANUP ------------------
pygame.quit()
s.close()