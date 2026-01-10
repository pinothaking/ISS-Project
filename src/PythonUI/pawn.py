import pygame
import math

class Pawn:
    def __init__(self, image_path, x, y, soul, aura, id, speed=2):
        self.image = pygame.image.load(image_path).convert_alpha()
        self.x = float(x)
        self.y = float(y)
        self.target_x = float(x)
        self.target_y = float(y)
        self.speed = speed
        self.arrived_threshold = 2.0  
        self.id = id
        
        # Statistiche Giocatore
        self.soul = soul
        self.aura = aura
        self.hp = 100  # Default HP, puoi cambiarlo se Java invia valori diversi

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
        if self.is_arrived():
            self.x = self.target_x
            self.y = self.target_y
            return

        dx = self.target_x - self.x
        dy = self.target_y - self.y
        dist = math.hypot(dx, dy)

        if dist == 0:
            return

        self.x += self.speed * dx / dist
        self.y += self.speed * dy / dist

    def draw(self, screen):
        """Disegna la pedina alle coordinate correnti"""
        screen.blit(self.image, (int(self.x), int(self.y)))

    def scale_image(self, size):
        """Ridimensiona l'immagine della pedina. size è una tupla (width, height)"""
        self.image = pygame.transform.scale(self.image, size)
    
    # Getter per le statistiche
    def get_soul(self):
        return self.soul
    
    def get_aura(self):
        return self.aura

    def get_hp(self):
        return self.hp