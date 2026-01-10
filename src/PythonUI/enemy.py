import pygame

class Enemy:
    def __init__(self, name, hp, image_path, x, y, scale=(100, 100)):
        """
        name: Nome del nemico (es. "Goblin")
        hp: Punti vita totali
        image_path: Percorso del file immagine (es. "img/goblin.png")
        x, y: Posizione sullo schermo
        scale: Dimensione (larghezza, altezza) per ridimensionare l'immagine
        """
        self.name = name
        self.max_hp = hp
        self.current_hp = hp
        self.x = x
        self.y = y
        
        # Carica e scala l'immagine
        try:
            raw_image = pygame.image.load(image_path).convert_alpha()
            self.image = pygame.transform.scale(raw_image, scale)
        except FileNotFoundError:
            print(f"ERRORE: Immagine {image_path} non trovata. Uso quadrato rosso.")
            self.image = pygame.Surface(scale)
            self.image.fill((255, 0, 0))

        self.rect = self.image.get_rect()
        self.rect.topleft = (x, y)
        
        # Font per il nome e gli HP
        self.font = pygame.font.SysFont("Arial", 20, bold=True)

    def take_damage(self, amount):
        """Riduce gli HP (per uso futuro)"""
        self.current_hp -= amount
        if self.current_hp < 0:
            self.current_hp = 0

    def draw(self, screen):
        # 1. Disegna l'immagine del nemico
        screen.blit(self.image, (self.x, self.y))

        # --- DISEGNO INTERFACCIA (UI) ---
        
        # Coordinate sopra la testa
        bar_x = self.x
        bar_y = self.y - 30 
        bar_width = self.image.get_width()
        bar_height = 10

        # 2. Scritta del Nome
        text_surf = self.font.render(f"{self.name}", True, (255, 255, 255))
        # Centra il testo sopra la barra
        text_rect = text_surf.get_rect(center=(self.x + bar_width//2, bar_y - 15))
        screen.blit(text_surf, text_rect)

        # 3. Barra della vita (Sfondo Rosso)
        pygame.draw.rect(screen, (100, 0, 0), (bar_x, bar_y, bar_width, bar_height))
        
        # 4. Barra della vita (Parte Verde - Calcolo percentuale)
        if self.max_hp > 0:
            hp_percentage = self.current_hp / self.max_hp
            current_bar_width = bar_width * hp_percentage
            pygame.draw.rect(screen, (0, 255, 0), (bar_x, bar_y, current_bar_width, bar_height))
        
        # Bordo nero intorno alla barra
        pygame.draw.rect(screen, (0, 0, 0), (bar_x, bar_y, bar_width, bar_height), 2)