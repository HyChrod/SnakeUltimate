package de.FScheunert.Snake.Mechanics;

import de.FScheunert.Snake.Snake;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class EntityHandler {

    private Snake snake;

    private LinkedList<Entity> livingEntitys = new LinkedList<>();

    private int overflow;

    public EntityHandler(Snake snake, int overflow) {
        this.overflow = overflow;
        this.snake = snake;
    }

    public int addEntity(Entity entity) {
        livingEntitys.add(entity);
        if (livingEntitys.size() > overflow) {
            livingEntitys.remove(2);
            return 1;
        }
        return 0;
    }

    public void render(Graphics g) {
        for(Entity en : new LinkedList<>(livingEntitys)) en.superRender(g);
    }

    public void tick() {
        for(Entity en : new LinkedList<>(livingEntitys)) en.tick();
    }

    public int[] newAvailablePosition() {
        int newPosX = new Random().nextInt(this.snake.SQUARE_INDEX_WIDTH) * this.snake.WIDTH_FACTOR;
        int newPosY = new Random().nextInt(this.snake.SQUARE_INDEX_HEIGHT) * this.snake.HEIGHT_FACTOR;
        return !isBlocked(newPosX, newPosY) ? new int[]{ newPosX, newPosY } : newAvailablePosition();
    }

    private boolean isBlocked(int posX, int posY) {
        for(Entity en : livingEntitys) if(en.getPosX() == posX && en.getPosY() == posY) return true;
        return false;
    }

    public int getOverflow() {
        return this.overflow;
    }

    public void setOverflow(int overflow) {
        this.overflow = overflow;
    }

    public LinkedList<Entity> getLivingEntitys() {
        return this.livingEntitys;
    }

}
