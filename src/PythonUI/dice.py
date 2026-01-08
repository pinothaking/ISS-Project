import pygame
import random
import time

class Dice_Move:
    def __init__(
        self,
        sprite_sheet_path,
        pos,
        frame_width=64,
        frame_height=64,
        num_frames=6,
        animation_speed=6
    ):
        self.sprite_sheet = pygame.image.load(sprite_sheet_path).convert_alpha()
        self.pos = pos

        self.frame_width = frame_width
        self.frame_height = frame_height
        self.num_frames = num_frames

        self.current_frame = 0
        self.animation_speed = animation_speed
        self.frame_counter = 0

        self.rolling = False
        self.result = None
        self.start_time = 0
        self.roll_duration = 0

    def throw(self, duration=1.5):
        """Avvia il lancio del dado"""
        self.rolling = True
        self.start_time = time.time()
        self.roll_duration = duration
        self.result = None

    def update(self):
        if self.rolling:
            self.frame_counter += 1
            if self.frame_counter >= self.animation_speed:
                self.current_frame = (self.current_frame + 1) % self.num_frames
                self.frame_counter = 0

            # Fine lancio
            if time.time() - self.start_time >= self.roll_duration:
                self.rolling = False
                self.current_frame = random.randint(0, self.num_frames - 1)
                self.result = self.current_frame + 1  # 1–6

    def draw(self, screen):
        x = self.current_frame * self.frame_width
        y = 0
        sprite = self.sprite_sheet.subsurface(
            (x, y, self.frame_width, self.frame_height)
        )
        screen.blit(sprite, self.pos)

    def get_number(self):
        return self.result
    def reset(self):
        """Dimentica il numero uscito per il prossimo turno"""
        self.result = None
