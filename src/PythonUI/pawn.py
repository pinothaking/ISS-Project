import pygame
import math

class Pawn:
    def __init__(self, image_path, x, y, id, speed=2):
        self.image = pygame.image.load(image_path).convert_alpha()
        self.x = float(x)
        self.y = float(y)
        self.target_x = float(x)
        self.target_y = float(y)
        self.speed = speed
        self.arrived_threshold = 2.0  # Soglia per considerare la pedina "arrivata"
        self.id = id

    def move_to(self, tx, ty):
        """Imposta una nuova posizione verso cui muoversi"""
        self.target_x = float(tx)
        self.target_y = float(ty)

    def is_arrived(self):
        """Ritorna True se la pedina ha raggiunto il target"""
        dx = self.target_x - self.x
        dy = self.target_y - self.y
        return math.hypot(dx, dy) < self.arrived_threshold

    def update(self):
        """Muove la pedina verso la posizione target"""
        # Se siamo già arrivati, ci assicuriamo di essere esattamente sul punto
        if self.is_arrived():
            self.x = self.target_x
            self.y = self.target_y
            return

        # Calcolo del vettore direzione
        dx = self.target_x - self.x
        dy = self.target_y - self.y
        dist = math.hypot(dx, dy)

        if dist == 0:
            return

        # Movimento
        self.x += self.speed * dx / dist
        self.y += self.speed * dy / dist

    def draw(self, screen):
        """Disegna la pedina alle coordinate correnti"""
        # Convertiamo in int perché i pixel non possono essere decimali
        screen.blit(self.image, (int(self.x), int(self.y)))

    def scale_image(self, size):
        """Ridimensiona l'immagine della pedina. size è una tupla (width, height)"""
        self.image = pygame.transform.scale(self.image, size)