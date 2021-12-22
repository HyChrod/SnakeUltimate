package de.FScheunert.Snake.Entitys;

import de.FScheunert.Snake.Mechanics.Entity;
import de.FScheunert.Snake.Snake;
import de.FScheunert.Snake.Utilities.GameState;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Head extends Entity {

    private int direction = 2;

    public Head(int posX, int posY, Color color, Snake snake) {
        super(posX, posY, color, snake);
    }

    @Override
    public void render(@NotNull Graphics g) {
        g.fillRect(getPosX(), getPosY(), getSnake().WIDTH_FACTOR, getSnake().HEIGHT_FACTOR);
    }

    @Override
    public void tick() {
        int oldX = getPosX();
        int oldY = getPosY();
        switch(direction) {
            case 0 -> setPosX(getPosX()-getSnake().WIDTH_FACTOR);
            case 1 -> setPosY(getPosY()-getSnake().HEIGHT_FACTOR);
            case 2 -> setPosX(getPosX()+getSnake().WIDTH_FACTOR);
            case 3 -> setPosY(getPosY()+getSnake().HEIGHT_FACTOR);
        }
        new Tail(oldX, oldY, getSnake());

        for(Entity en : getSnake().getEntityHandler().getLivingEntitys()) {
            if(en.getUniqueId().equals(this.getUniqueId())
                    || en.getUniqueId().equals(getSnake().getApple().getUniqueId())) continue;
            if(en.getPosY() == getPosY() && en.getPosX() == getPosX()) GameState.END.setActive();
        }
    }

    public void setDirection(int direction, boolean force) {
        if(!force && (this.direction == 0 && direction == 2 || this.direction == 2 && direction == 0
                || this.direction == 1 && direction == 3 || this.direction == 3 && direction == 1)) return;
        this.direction = direction;
    }

}
