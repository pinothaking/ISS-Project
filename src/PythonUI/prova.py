import socket
import pygame
import math
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
    8: (357, 656),
    9: (415, 651),
    10: (451, 623),
    11: (460, 599),
    12: (433, 582),
    13: (394, 581),
    14: (342, 579),
    15: (290, 577),
    16: (250, 570),
    17: (220, 549),
    18: (233, 523),
    19: (268, 517),
    20: (318, 518),
    21: (357, 520),
    22: (403, 526),
}

# ------------------ INIT PYGAME ------------------
pygame.init()
WIDTH, HEIGHT = 620, 760
background_colour = (234, 212, 252) 
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption('The Three Kingdoms')
clock = pygame.time.Clock()

# ------------------ FONT ------------------
myfont = pygame.font.SysFont("lucidasanstypewriter", 35)
myfont_small = pygame.font.SysFont("lucidasanstypewriter", 25) 
myfont_big = pygame.font.SysFont("lucidasanstypewriter", 45)
title_font = pygame.font.SysFont("lucidasanstypewriter", 60, bold=True)
combat_font = pygame.font.SysFont("lucidasanstypewriter", 70, bold=True)
log_font = pygame.font.SysFont("lucidasanstypewriter", 24, bold=True) 

# ------------------ VARIABILI GLOBALI ------------------
label = None
label_turn = None
label_event = None 
Map = None
BattleMap = None

# Stato Giocatori
num_players = 0
current_player = 1
player_selection_done = False
can_move = False

# --- STATI BATTAGLIA ---
in_battle = False 
battle_state = "PLAYER_WAIT" 
battle_timer = 0
battle_log_text = "Inizia il combattimento!" 

# Gestione Pedine e Posizioni
pawns = []                 
player_logical_pos = {}    

# Stato Movimento ed Eventi
movement_queue = []        
pending_event = None       
current_enemy = None       

# ------------------ ASSETS & ANIMAZIONI ------------------
dice = Dice_Move("img/Dice-Sheet.png", pos=(10, 450)) 
dice_result_sent = False
dice_enemy = Dice_Move("img/Dice-Sheet-Enemy.png", pos=(10, 550)) 

soul_animation = SpriteAnimation("img/WhiteFlame.png", 0, 0, 32, 32, scale=3, speed=120)
aura = SpriteAnimation("img/AuraPoints-Sheet.png", 0, 0, 32, 32, scale=3, speed=120)
health = SpriteAnimation("img/HeartInfernothings.png", 0, 0 , 32, 32 , scale=3 , speed=0) 

dialogbox = SpriteAnimation("img/dialogbox.png", 0 ,0, 620, 760, scale=1, speed=0)

# ------------------ SOCKET CONNECTION ------------------
SOCKET_PATH = "/tmp/game_socket"
s = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)
try:
    s.connect(SOCKET_PATH)
    s.setblocking(False) 
except FileNotFoundError:
    print("ERRORE CRITICO: Il server Java non è avviato o il socket non esiste.")
    exit()

# =============================================================================
#                                  MAIN LOOP
# =============================================================================
running = True
while running:

    # ------------------ 1. GESTIONE EVENTI (INPUT) ------------------
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

        # A) MENU INIZIALE
        if event.type == pygame.KEYDOWN and not player_selection_done:
            if event.key in (pygame.K_2, pygame.K_3, pygame.K_4):
                num_players = int(event.unicode)
                s.sendall(f"PLAYERS:{num_players}\n".encode())

        # B) GIOCO & BATTAGLIA
        if event.type == pygame.KEYDOWN:
            
            # --- MENU VITTORIA ---
            if in_battle and battle_state == "VICTORY_SELECTION":
                current_pawn = pawns[current_player - 1]
                
                if event.key == pygame.K_1:
                    print("Hai scelto: LIBERA SOUL")
                    current_pawn.soul += 1   
                    current_pawn.aura += 10  
                    current_enemy = None
                    in_battle = False 
                    label_event = None
                    s.sendall(b"MOVE_DONE\n")
                    
                elif event.key == pygame.K_2:
                    print("Hai scelto: RUBA SOUL")
                    current_enemy = None
                    in_battle = False 
                    label_event = None
                    s.sendall(b"MOVE_DONE\n")

            # --- COMBATTIMENTO: ATTACCO ---
            elif in_battle and battle_state == "PLAYER_WAIT" and event.key == pygame.K_SPACE:
                dice.reset()     
                dice.throw(2)    
                battle_state = "PLAYER_ANIMATION"
                battle_log_text = "Lanci il dado..." 

            # --- MOVIMENTO MAPPA ---
            elif not in_battle and can_move and event.key == pygame.K_SPACE:
                 if dice.get_number() is None and len(movement_queue) == 0 and pending_event is None:
                    print(f"🎲 Player {current_player} lancia il dado movimento...")
                    can_move = False     
                    dice.throw(2)        
                    dice_result_sent = False
                    label_event = None   
        
        # C) CHEAT VITTORIA RAPIDA (K)
        if event.type == pygame.KEYDOWN and event.key == pygame.K_k:
            if in_battle and current_enemy is not None:
                current_enemy.current_hp = 0
                battle_state = "VICTORY_SELECTION" 

    # ------------------ 2. RICEZIONE SOCKET (JAVA) ------------------
    try:
        data = s.recv(4096)
        if data:
            commands = data.decode().strip().split("\n")
            for cmd in commands:
                if not cmd: continue 
                
                print(f"Da Java: {cmd}") 

                if cmd == "REQUEST_PLAYERS":
                    pass 

                elif cmd == "DRAW_BOARD":
                    try:
                        Map = pygame.image.load("img/TheThreeReign.png")
                        Map = pygame.transform.scale(Map, (WIDTH, HEIGHT))
                        try:
                            BattleMap = pygame.image.load("img/battle_arena.png")
                            BattleMap = pygame.transform.scale(BattleMap, (WIDTH, HEIGHT))
                        except:
                            BattleMap = None
                    except:
                        print("Err: Immagine mappa non trovata")

                elif cmd.startswith("SET_PLAYERS:"):
                    num_players = int(cmd.split(":")[1])
                    player_selection_done = True 
                    pawns.clear()
                    player_logical_pos.clear()
                    start_x, start_y = TILE_COORDINATES.get(0, (0,0))
                    
                    for i in range(num_players):
                        new_pawn = Pawn("img/Pawn.png", start_x, start_y, soul=0, aura=0, id=i+1, speed=3)
                        new_pawn.scale_image((40, 40)) 
                        pawns.append(new_pawn)
                        player_logical_pos[i+1] = 0 
                    print(f"Setup completato.")

                elif cmd.startswith("SET_TURN:"):
                    current_player = int(cmd.split(":")[1])

                elif cmd.startswith("MOVE_PAWN_TO:"):
                    target_id = int(cmd.split(":")[1])
                    current_logic_pos = player_logical_pos.get(current_player, 0)
                    start_range = current_logic_pos + 1
                    end_range = target_id + 1
                    if target_id < current_logic_pos:
                         start_range = target_id 
                         end_range = target_id + 1

                    for i in range(start_range, end_range):
                        if i in TILE_COORDINATES:
                            movement_queue.append(TILE_COORDINATES[i])
                    player_logical_pos[current_player] = target_id
                
                elif cmd.startswith("EVENT:"):
                    pending_event = cmd
                    print(f"Evento ricevuto: {cmd}")

                elif cmd == "THROWDICE":
                    dice.throw(2)
                    dice_result_sent = False

                elif cmd == "WAIT_PLAYER":
                    can_move = True
                    dice.reset() 
                    if current_player == 1:
                        label_turn = myfont_big.render(f"Tocca a P{current_player}", 1, (0, 255, 0))
                    else:
                        label_turn = myfont_big.render(f"Tocca a P{current_player}", 1, (255, 215, 0))

    except BlockingIOError:
        pass
    except OSError:
        running = False

    # ------------------ 3. AGGIORNAMENTI LOGICI (UPDATE) ------------------
    dice.update()
    dice_enemy.update()
    
    if player_selection_done:
        soul_animation.play()
        aura.play()
        health.play()
        
        for p in pawns:
            p.update()
        
        # --- MOVIMENTO MAPPA ---
        if pawns and not in_battle:
            active_pawn = pawns[current_player - 1] 
            if active_pawn.is_arrived() and len(movement_queue) > 0:
                next_x, next_y = movement_queue.pop(0) 
                active_pawn.move_to(next_x, next_y)    
        
        # --- GESTIONE EVENTI (FINE MOVIMENTO) ---
        if pawns and pawns[current_player-1].is_arrived() and len(movement_queue) == 0 and pending_event is not None:
            
            event_type = pending_event
            pending_event = None # Reset evento
            
            # --- EVENTI CLASSICI ---
            if event_type == "EVENT:ENEMY":
                combat_text = combat_font.render("COMBATTIMENTO!", True, (255, 0, 0))
                text_rect = combat_text.get_rect(center=(WIDTH//2, HEIGHT//2))
                s_surf = pygame.Surface((WIDTH, 200))  
                s_surf.set_alpha(180)                
                s_surf.fill((0,0,0))           
                screen.blit(s_surf, (0, HEIGHT//2 - 100))
                screen.blit(combat_text, text_rect)
                pygame.display.flip()
                pygame.time.delay(1000)
                
                # Inizializza Battaglia
                in_battle = True
                battle_state = "PLAYER_WAIT" 
                battle_log_text = "Un nemico appare! Premi SPAZIO."
                dice.reset()       
                dice_enemy.reset()
                
                current_enemy = Enemy("Guardian", 100, "img/KingMorlott.png", 80, 250, scale=(450, 450))
                
            elif event_type == "EVENT:SHOP":
                label_event = myfont_big.render("NEGOZIO!", 1, (0, 255, 0))
                s.sendall(b"MOVE_DONE\n")
                
            elif event_type == "EVENT:EMPTY":
                label_event = None
                s.sendall(b"MOVE_DONE\n")
            
            # --- EVENTO SPECIALE: FINE ARRETRAMENTO ---
            # Questo viene chiamato quando il giocatore ha finito di camminare all'indietro
            elif event_type == "INTERNAL:RETREAT":
                print("Arretramento completato. Passo il turno.")
                s.sendall(b"MOVE_DONE\n")

    # =========================================================
    #               LOGICA DI BATTAGLIA A TURNI
    # =========================================================
    if in_battle and current_enemy and pawns and battle_state != "VICTORY_SELECTION":
        current_pawn = pawns[current_player - 1]

        # 1. Animazione Dado Giocatore finita?
        if battle_state == "PLAYER_ANIMATION" and dice.get_number() is not None:
            raw_roll = dice.get_number()
            damage = raw_roll * 10 
            
            battle_log_text = f"Hai fatto {raw_roll}! Infliggi {damage} danni."
            
            current_enemy.take_damage(damage) 
            
            if current_enemy.current_hp <= 0:
                battle_state = "VICTORY_SELECTION" 
            else:
                battle_state = "ENEMY_WAIT"
                battle_timer = pygame.time.get_ticks() 

        # 2. Attesa Nemico
        elif battle_state == "ENEMY_WAIT":
            if pygame.time.get_ticks() - battle_timer > 1500: 
                battle_log_text = "Il nemico si prepara ad attaccare..."
                dice_enemy.reset()
                dice_enemy.throw(2)
                battle_state = "ENEMY_ANIMATION"

        # 3. Animazione Dado Nemico finita?
        elif battle_state == "ENEMY_ANIMATION" and dice_enemy.get_number() is not None:
            raw_roll = dice_enemy.get_number()
            damage = raw_roll * 10 
            
            battle_log_text = f"Nemico tira {raw_roll}. Subisci {damage} danni!"
            
            current_pawn.hp -= damage 
            if current_pawn.hp < 0: current_pawn.hp = 0

            # --- SCONFITTA ---
            if current_pawn.hp <= 0:
                print("SCONFITTA! Reset HP e Arretramento.")
                current_pawn.hp = 100 
                
                # Calcolo posizione indietro
                current_pos_id = player_logical_pos.get(current_player, 0)
                back_pos_id = max(0, current_pos_id - 3)
                
                # Aggiornamento Logico
                player_logical_pos[current_player] = back_pos_id
                
                # RIEMPI LA CODA DI MOVIMENTO INDIETRO
                # Nota: range(start, end, -1) conta all'indietro
                start_back = current_pos_id - 1
                end_back = back_pos_id - 1 
                
                for i in range(start_back, end_back, -1):
                    if i in TILE_COORDINATES:
                        movement_queue.append(TILE_COORDINATES[i])
                
                # Chiudiamo la battaglia
                in_battle = False
                current_enemy = None
                
                # IMPORTANTE: NON inviare MOVE_DONE qui!
                # Impostiamo un evento pendente che scatterà quando l'animazione indietro finisce.
                pending_event = "INTERNAL:RETREAT"
                
            else:
                battle_state = "PLAYER_WAIT"
                dice.reset() 

    # =========================================================

    # Invio risultato dado (SOLO MOVIMENTO)
    if not in_battle:
        dice_num = dice.get_number()
        if dice_num is not None and not dice_result_sent:
            s.sendall(f"DICE_RESULT:{dice_num}\n".encode())
            dice_result_sent = True

    # ------------------ 4. DISEGNO (DRAW) ------------------
    if not player_selection_done:
        # MENU
        screen.fill((0, 0, 0)) 
        title_text = title_font.render("L' ASCESA", True, (255, 215, 0)) 
        title_rect = title_text.get_rect(center=(310, 200))
        screen.blit(title_text, title_rect)
        soul_animation.set_position(290, 300)
        soul_animation.play()
        soul_animation.draw(screen)
        instr = myfont.render("Seleziona numero giocatori:", True, (200, 200, 200))
        screen.blit(instr, (80, 450))
        opts = myfont_big.render("[ 2 ]    [ 3 ]    [ 4 ]", True, (100, 200, 255))
        screen.blit(opts, (80, 520))

    else:
        # GIOCO
        if in_battle:
            
            if battle_state == "VICTORY_SELECTION":
                screen.fill((0, 0, 0)) 
                vic_text = combat_font.render("NEMICO SCONFITTO", True, (255, 215, 0)) 
                vic_rect = vic_text.get_rect(center=(WIDTH//2, 150))
                screen.blit(vic_text, vic_rect)
                sub_text = myfont.render("Scegli la tua ricompensa:", True, (255, 255, 255))
                sub_rect = sub_text.get_rect(center=(WIDTH//2, 250))
                screen.blit(sub_text, sub_rect)
                opt1 = myfont_big.render("[1] LIBERA SOUL (+10 Aura)", True, (100, 255, 100))
                screen.blit(opt1, (50, 400))
                opt2 = myfont_big.render("[2] RUBA SOUL", True, (255, 100, 100))
                screen.blit(opt2, (50, 500))
            
            else:
                if BattleMap: screen.blit(BattleMap, (0, 0))
                else: screen.fill((50, 20, 20))
                
                if current_enemy: current_enemy.draw(screen)
                
                dialogbox.draw(screen)
                log_surf = log_font.render(battle_log_text, True, (255, 255, 255))
                log_rect = log_surf.get_rect(center=(WIDTH//2, 700)) 
                screen.blit(log_surf, log_rect)
                
                dice.draw(screen)
                dice_enemy.draw(screen)
                
                # HUD Stats
                current_pawn_obj = pawns[current_player - 1]
                soul_animation.set_position(10, 20)
                soul_animation.draw(screen)
                txt_soul = myfont_small.render(f"{current_pawn_obj.get_soul()}", True, (255, 255, 255))
                screen.blit(txt_soul, (90, 50)) 
                aura.set_position(10, 120)
                aura.draw(screen)
                txt_aura = myfont_small.render(f"{current_pawn_obj.get_aura()}", True, (255, 255, 255))
                screen.blit(txt_aura, (100, 150)) 
                health.set_position(100, 20)
                health.draw(screen)
                txt_hp = myfont_small.render(f"{current_pawn_obj.get_hp()}", True, (255, 50, 50)) 
                screen.blit(txt_hp, (180, 50)) 

        else:
            # --- MODALITÀ MAPPA ---
            screen.fill(background_colour) 
            if Map: screen.blit(Map, (0, 0))
            for p in pawns: p.draw(screen)
            dice.draw(screen)

            # HUD
            current_pawn_obj = pawns[current_player - 1]
            soul_animation.set_position(10, 20)
            soul_animation.draw(screen)
            txt_soul = myfont_small.render(f"{current_pawn_obj.get_soul()}", True, (255, 255, 255))
            screen.blit(txt_soul, (90, 50)) 
            aura.set_position(10, 120)
            aura.draw(screen)
            txt_aura = myfont_small.render(f"{current_pawn_obj.get_aura()}", True, (255, 255, 255))
            screen.blit(txt_aura, (100, 150)) 
            health.set_position(100, 20)
            health.draw(screen)
            txt_hp = myfont_small.render(f"{current_pawn_obj.get_hp()}", True, (255, 50, 50)) 
            screen.blit(txt_hp, (180, 50)) 

            # UI
            if label: screen.blit(label, (0, 500))
            if label_turn: screen.blit(label_turn, (0, 550))
            if label_event: screen.blit(label_event, (150, 300))
            
            if can_move and dice.get_number() is None and not movement_queue and not pending_event:
                hint = myfont.render("PREMI SPAZIO", 1, (255, 255, 255))
                screen.blit(hint, (0, 600))

    pygame.display.flip()
    clock.tick(60)

pygame.quit()
s.close()