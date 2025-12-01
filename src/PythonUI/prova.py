import socket
import pygame


pygame.init()
background_colour = (234, 212, 252)
screen = pygame.display.set_mode((620, 760))
pygame.display.set_caption('The Three Kingdoms')
screen.fill(background_colour)
pygame.display.flip()


Map = None


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
            cmd = data.decode().strip()
            print(f"Comando da Java: {cmd}")
            if cmd.startswith("DRAW_BOARD"):
                Map = pygame.image.load("img/TheThreeReign.png")
                Map = pygame.transform.scale(Map, (620, 760))
            elif cmd.startswith("MOVE_PIECE"):
                print("→ Muovo la pedina:", cmd.split()[1:])
            elif cmd.startswith("WAIT_PLAYER"):
                print("→ Mostro messaggio: attendi il giocatore...")
            s.sendall(b"OK\n")
    except BlockingIOError:
        pass 

    
    screen.fill(background_colour)
    if Map:
        screen.blit(Map, (0, 0))
    pygame.display.flip()
