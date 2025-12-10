import pygame
pygame.init()

screen = pygame.display.set_mode((800, 600))
pygame.display.set_caption("Sprite Animation")

sprite_sheet = pygame.image.load("img/dice_sprite_sheet.png").convert_alpha()

frame_width = 200
frame_height = 190
num_frames = 6  # Numero totale di frame

# Per l'animazione
current_frame = 0
animation_speed = 10  # Più basso = più veloce
frame_counter = 0

running = True
clock = pygame.time.Clock()

while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
    
    # Animazione automatica
    frame_counter += 1
    if frame_counter >= animation_speed:
        current_frame = (current_frame + 1) % num_frames
        frame_counter = 0
    
    # Calcola posizione (se i frame sono in riga)
    x = current_frame * frame_width
    y = 0
    
    # Se i frame sono su più righe:
    # colonne = 3  # frame per riga
    # x = (current_frame % colonne) * frame_width
    # y = (current_frame // colonne) * frame_height
    
    sprite = sprite_sheet.subsurface((x, y, frame_width, frame_height))
    
    screen.fill((100, 100, 100))
    screen.blit(sprite, (300, 200))
    
    pygame.display.flip()
    clock.tick(60)

pygame.quit()