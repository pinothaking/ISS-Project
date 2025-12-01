# pawn.py
import pygame
import math

class Pawn:
    def __init__(self, image_path, x, y, speed=5):
        self.image = pygame.image.load(image_path).convert_alpha()
        self.x = float(x)
        self.y = float(y)
        self.target_x = float(x)
        self.target_y = float(y)
        self.speed = speed
        self.arrived_threshold = 1.0

    def move_to(self, tx, ty):
        """Imposta una nuova posizione verso cui muoversi"""
        self.target_x = float(tx)
        self.target_y = float(ty)

    def update(self):
        """Muove la pedina verso la posizione target"""
        dx = self.target_x - self.x
        dy = self.target_y - self.y

        if abs(dx) < self.arrived_threshold and abs(dy) < self.arrived_threshold:
            # Arrivato
            self.x = self.target_x
            self.y = self.target_y
            return

        dist = math.hypot(dx, dy)
        if dist == 0:
            return

        self.x += self.speed * dx / dist
        self.y += self.speed * dy / dist

    def draw(self, screen):
        # usa int() per le coordinate di blit
        screen.blit(self.image, (int(self.x), int(self.y)))

    def scale_image(self, size):
        """Ridimensiona l'immagine della pedina (size = (w,h))"""
        self.image = pygame.transform.scale(self.image, size)
