import socket
import pygame
from pawn import Pawn
from dice import Dice_Move

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
Map = None

pawn = Pawn("img/Pawn.png", 367, 700, 1, speed=0.5)

num_players = 0
current_player = 1
player_selection_done = False
can_move = False

# ------------------ DICE ------------------
dice = Dice_Move("img/Dice-Sheet.png", pos=(300, 200))
dice_result_sent = False

# ------------------ SOCKET ------------------
SOCKET_PATH = "/tmp/game_socket"
s = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)
s.connect(SOCKET_PATH)
s.setblocking(False)

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

        # SPAZIO = LANCIO DADO
        if event.type == pygame.KEYDOWN and event.key == pygame.K_SPACE:
            if can_move and dice.get_number() is None:
                print("🎲 Lancio dado")
                dice.throw(2)
                dice_result_sent = False

    # -------- SOCKET RECEIVE --------
    try:
        data = s.recv(1024)
        if data:
            for cmd in data.decode().strip().split("\n"):
                print("Da Java:", cmd)

                if cmd == "REQUEST_PLAYERS":
                    label_start = myfont.render(
                        "Premi 2, 3 o 4 per scegliere", 1, (0, 0, 0)
                    )

                elif cmd == "DRAW_BOARD":
                    Map = pygame.image.load("img/TheThreeReign.png")
                    Map = pygame.transform.scale(Map, (620, 760))

                elif cmd.startswith("SET_PLAYERS:"):
                    num_players = int(cmd.split(":")[1])
                    label = myfont.render(
                        f"N Player: {num_players}", 1, (255, 255, 255)
                    )
                    label_start = None

                elif cmd.startswith("SET_TURN:"):
                    current_player = int(cmd.split(":")[1])
                    label_turn = myfont_big.render(
                        f"Turno di Player {current_player}", 1, (255, 215, 0)
                    )
                    can_move = True

                elif cmd == "THROWDICE":
                    # Java può forzare il lancio
                    dice.throw(2)
                    dice_result_sent = False

                elif cmd == "WAIT_PLAYER":
                    can_move = False
                    label_turn = myfont_big.render(
                        "Attendere...", 1, (255, 100, 100)
                    )

            s.sendall(b"OK\n")

    except BlockingIOError:
        pass
    except OSError:
        running = False

    # -------- UPDATE --------
    pawn.update()
    dice.update()

    # 🎲 RISULTATO DADO
    dice_number = dice.get_number()
    if dice_number is not None and not dice_result_sent:
        print(f"🎲 Dado uscito: {dice_number}")
        s.sendall(f"DICE_RESULT:{dice_number}\n".encode())
        dice_result_sent = True

        # QUI Java ora decide cosa fare
        # (muovere pawn, cambiare turno, ecc.)

    # -------- DRAW --------
    screen.fill(background_colour)

    if Map:
        screen.blit(Map, (0, 0))

    pawn.draw(screen)
    dice.draw(screen)

    if label:
        screen.blit(label, (0, 400))

    if label_start:
        screen.blit(label_start, (120, 360))

    if label_turn:
        screen.blit(label_turn, (0, 420))

    if can_move and dice.get_number() is None:
        instruction = myfont.render(
            "Premi SPAZIO per lanciare il dado", 1, (255, 255, 255)
        )
        screen.blit(instruction, (0, 460))

    pygame.display.flip()
    clock.tick(60)

# ------------------ CLEANUP ------------------
pygame.quit()
s.close()
