package de.FScheunert.Snake.Mechanics;

import de.FScheunert.Snake.Snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

    private Snake snake;

    public KeyboardListener(Snake snake) {
        this.snake = snake;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 37,65 -> this.snake.getSnakeHead().setDirection(0);
            case 38,87 -> this.snake.getSnakeHead().setDirection(1);
            case 39,68 -> this.snake.getSnakeHead().setDirection(2);
            case 40,83 -> this.snake.getSnakeHead().setDirection(3);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
