package de.FScheunert.Snake.Mechanics;

import de.FScheunert.Snake.Snake;
import de.FScheunert.Snake.Utilities.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseListeners implements MouseListener, MouseMotionListener {

    private Snake snake;

    public MouseListeners(Snake snake) {
        this.snake = snake;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(DynamicElement element : getSnake().getDynamicHandler().getElementsRendered().stream()
                .filter(i -> i instanceof DynamicButton).filter(i -> i.getState().isActive()).toList()) {
            DynamicButton button = (DynamicButton) element;
            if(intersects(e.getX(),e.getY(),button.getPosX(),button.getPosY(), button.getWidth(), button.getHeight()))
                button.interact();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        if(GameState.INGAME.isActive()) return;

        for(DynamicElement element : getSnake().getDynamicHandler().getElementsRendered().stream()
                .filter(i -> i instanceof DynamicButton).toList()) {
            DynamicButton button = (DynamicButton) element;
            if(intersects(x,y,button.getPosX(),button.getPosY(), button.getWidth(), button.getHeight()))
                button.setHovered(true);
            else button.setHovered(false);
        }
    }

    private boolean intersects(int mX, int mY, int x, int y, int width, int height) {
        return (mX >= x && mX <= (x+width) && mY >= y && mY <= (y+height));
    }

    public Snake getSnake() {
        return snake;
    }
}