package de.FScheunert.Snake.Entitys;

import de.FScheunert.Snake.Mechanics.Entity;
import de.FScheunert.Snake.Snake;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Tail extends Entity {

    public Tail(int posX, int posY, Snake snake) {
        super(posX, posY, Color.YELLOW, snake);
    }

    @Override
    public void render(@NotNull Graphics g) {
        g.fillRect(getPosX(), getPosY(), getSnake().WIDTH_FACTOR, getSnake().HEIGHT_FACTOR);
    }

    @Override
    public void tick() {}

}
