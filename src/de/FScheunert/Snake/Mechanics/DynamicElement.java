package de.FScheunert.Snake.Mechanics;

import de.FScheunert.Snake.Snake;
import de.FScheunert.Snake.Utilities.GameState;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class DynamicElement {

    private final int posX, posY;
    private final double relX, relY;
    private final int width, height;
    private final GameState state;
    private final Snake snake;

    public DynamicElement(double relX, double relY, double relWidth, double relHeight, @NotNull Snake snake, GameState state) {
        this.relX = relX;
        this.relY = relY;
        this.state = state;
        this.snake = snake;

        this.width = (int) (snake.getFrameBuilder().getWidth()*relWidth);
        this.height = (int) (snake.getFrameBuilder().getHeight()*relHeight);
        this.posX = (int) (snake.getFrameBuilder().getWidth()*relX - (getWidth()/2));
        this.posY = (int) (snake.getFrameBuilder().getHeight()*relY - (getHeight()/2));

        snake.getDynamicHandler().addElement(this);
    }

    public void preRender(Graphics g) {
        if(state.isActive()) render(g);
    }

    public abstract void render(Graphics g);

    public void preTick() {
        if(state.isActive()) tick();
    }

    public abstract void tick();

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public double getRelX() {
        return relX;
    }

    public double getRelY() {
        return relY;
    }

    public GameState getState() {
        return state;
    }

    public Snake getSnake() {
        return snake;
    }
}
