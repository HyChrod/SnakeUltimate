package de.FScheunert.Snake.Mechanics;

import de.FScheunert.Snake.Snake;
import de.FScheunert.Snake.Utilities.GameState;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public record KeyboardListener(Snake snake) implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(@NotNull KeyEvent e) {
        switch (e.getKeyCode()) {
            case 37, 65 -> setDirection(0);
            case 38, 87 -> setDirection(1);
            case 39, 68 -> setDirection(2);
            case 40, 83 -> setDirection(3);
            case 27, 80 -> {
                if (GameState.MENU.isActive() || GameState.END.isActive()) return;
                GameState.setGameState(GameState.INGAME.isActive() ? GameState.PAUSE : GameState.INGAME);
            }
        }
    }

    private void setDirection(int direction) {
        if (GameState.INGAME.isActive()) this.snake.getSnakeHead().setDirection(direction, false);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
