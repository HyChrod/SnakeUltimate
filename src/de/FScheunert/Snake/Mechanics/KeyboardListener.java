package de.FScheunert.Snake.Mechanics;

import de.FScheunert.Snake.Snake;
import de.FScheunert.Snake.Utilities.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public record KeyboardListener(Snake snake) implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 37, 65 -> {
                if (GameState.INGAME.isActive()) this.snake.getSnakeHead().setDirection(0, false);
            }
            case 38, 87 -> {
                if (GameState.INGAME.isActive()) this.snake.getSnakeHead().setDirection(1, false);
            }
            case 39, 68 -> {
                if (GameState.INGAME.isActive()) this.snake.getSnakeHead().setDirection(2, false);
            }
            case 40, 83 -> {
                if (GameState.INGAME.isActive()) this.snake.getSnakeHead().setDirection(3, false);
            }
            case 27, 80 -> {
                if (GameState.MENU.isActive() || GameState.END.isActive()) return;
                GameState.setGameState(GameState.INGAME.isActive() ? GameState.PAUSE : GameState.INGAME);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
