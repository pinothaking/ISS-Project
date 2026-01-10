import pygame

class SpriteAnimation:
    def __init__(self, filename, x, y, frame_width, frame_height, scale=1, speed=100):
        """
        filename: Percorso del file immagine (es. "img/fire_sheet.png")
        x, y: Posizione iniziale sullo schermo
        frame_width, frame_height: Dimensione di un SINGOLO quadratino (es. 32x32)
        scale: Quanto ingrandire l'immagine (es. 2 o 3 volte)
        speed: Millisecondi tra un frame e l'altro (più basso = più veloce)
        """
        self.sheet = pygame.image.load(filename).convert_alpha()
        self.frames = []
        self.current_frame = 0
        self.last_update = pygame.time.get_ticks() # Timer interno
        self.speed = speed # Velocità animazione in ms
        self.x = x
        self.y = y

        # --- TAGLIO DEI FRAME ---
        sheet_width = self.sheet.get_width()
        
        # Calcola quanti frame ci sono nell'immagine basandosi sulla larghezza
        for i in range(0, sheet_width, frame_width):
            # 1. Crea una superficie vuota grande quanto un frame
            frame = pygame.Surface((frame_width, frame_height), pygame.SRCALPHA)
            
            # 2. Copia (blit) solo il pezzetto che ci interessa dallo sheet al frame
            # L'area di ritaglio è (x, y, larghezza, altezza) -> (i, 0, frame_width, frame_height)
            frame.blit(self.sheet, (0, 0), (i, 0, frame_width, frame_height))
            
            # 3. Scaliamo il frame se necessario (perché 32x32 è piccolo)
            if scale != 1:
                frame = pygame.transform.scale(frame, (frame_width * scale, frame_height * scale))
            
            self.frames.append(frame)

    def set_position(self, x, y):
        """Aggiorna la posizione se l'oggetto si muove"""
        self.x = x
        self.y = y

    def play(self):
        """Calcola quale frame mostrare in base al tempo passato"""
        now = pygame.time.get_ticks()
        
        # Se è passato abbastanza tempo, cambia frame
        if now - self.last_update > self.speed:
            self.last_update = now
            # Avanza di 1. L'operatore % (modulo) fa tornare a 0 quando arriva alla fine
            self.current_frame = (self.current_frame + 1) % len(self.frames)

    def draw(self, screen):
        """Disegna il frame corrente"""
        if self.frames:
            screen.blit(self.frames[self.current_frame], (self.x, self.y))