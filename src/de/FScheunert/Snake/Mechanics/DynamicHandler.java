package de.FScheunert.Snake.Mechanics;

import de.FScheunert.Snake.Snake;

import java.awt.*;
import java.util.LinkedList;

public class DynamicHandler {

    private Snake snake;

    private LinkedList<DynamicElement> elementsRendered = new LinkedList<>();

    public DynamicHandler(Snake snake) {
        this.snake = snake;
    }

    public void addElement(DynamicElement element) {
        this.elementsRendered.add(element);
    }

    public void render(Graphics g) {
        for(DynamicElement element : elementsRendered) element.preRender(g);
    }

    public void tick() {
        for(DynamicElement element : elementsRendered) element.preTick();
    }

    public Snake getSnake() {
        return snake;
    }

    public LinkedList<DynamicElement> getElementsRendered() {
        return elementsRendered;
    }

}
