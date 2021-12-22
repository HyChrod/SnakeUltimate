package de.FScheunert.Snake.Entitys;

import de.FScheunert.Snake.Mechanics.Entity;
import de.FScheunert.Snake.Snake;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Apple extends Entity {

    public Apple(int posX, int posY, Color color, Snake snake) {
        super(posX, posY, color, snake);
    }

    @Override
    public void render(@NotNull Graphics g) {
        g.fillRect(getPosX(), getPosY(), getSnake().WIDTH_FACTOR, getSnake().HEIGHT_FACTOR);
    }

    @Override
    public void tick() {
        if(getPosX() == getSnake().getSnakeHead().getPosX()
                && getPosY() == getSnake().getSnakeHead().getPosY()) {
            int[] newPosition = getSnake().getEntityHandler().newAvailablePosition();
            setPosX(newPosition[0]);
            setPosY(newPosition[1]);
            getSnake().getEntityHandler().setOverflow(getSnake().getEntityHandler().getOverflow()+1);
        }
    }
}
