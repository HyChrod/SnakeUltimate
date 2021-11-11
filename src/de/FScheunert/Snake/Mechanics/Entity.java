package de.FScheunert.Snake.Mechanics;

import de.FScheunert.Snake.Snake;

import java.awt.*;
import java.util.UUID;

public abstract class Entity {

    private UUID uniqueId = UUID.randomUUID();

    private int posX;
    private int posY;

    private Color color;

    private Snake snake;

    public Entity(int posX, int posY, Color color, Snake snake) {
        this.posX = posX;
        this.posY = posY;
        this.color = color;
        this.snake = snake;
        snake.getEntityHandler().addEntity(this);
    }

    protected void superRender(Graphics g) {
        g.setColor(getColor());
        render(g);
    }

    public abstract void render(Graphics g);

    public abstract void tick();

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Color getColor() {
        return color;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setPosX(int newX) {
        this.posX = validatePos(newX, getSnake().ENTITY_MAX_X);
    }

    public void setPosY(int newY) {
        this.posY = validatePos(newY, getSnake().ENTITY_MAX_Y);
    }

    private int validatePos(int newPosValue, int maxOverflow) {
        int toReturn = getSnake().BORDER_OVERPASS && newPosValue < 0 ? maxOverflow : Math.max(newPosValue, 0);
        return getSnake().BORDER_OVERPASS && toReturn > maxOverflow ? 0 : Math.min(toReturn, maxOverflow);
    }

}
