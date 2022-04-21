package de.FScheunert.Snake.Mechanics;

import de.FScheunert.Snake.Snake;
import de.FScheunert.Snake.Utilities.GameState;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public record MouseListeners(Snake snake) implements MouseListener, MouseMotionListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        for (DynamicElement element : getSnake().getDynamicHandler().getElementsRendered().stream()
                .filter(i -> i instanceof DynamicButton).filter(i -> i.getState().isActive()).toList()) {
            DynamicButton button = (DynamicButton) element;
            if (intersects(e.getX(), e.getY(), button.getPosX(), button.getPosY(), button.getWidth(), button.getHeight()))
                button.interact();
        }
    }

    @Override
    public void mouseMoved(@NotNull MouseEvent e) {
        int x = e.getX(), y = e.getY();
        if (GameState.INGAME.isActive() || getSnake().getDynamicHandler() == null) return;

        for (DynamicElement element : getSnake().getDynamicHandler().getElementsRendered().stream()
                .filter(i -> i instanceof DynamicButton).toList()) {
            DynamicButton button = (DynamicButton) element;
            button.setHovered(intersects(x, y, button.getPosX(), button.getPosY(), button.getWidth(), button.getHeight()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    private boolean intersects(int mX, int mY, int x, int y, int width, int height) {
        return (mX >= x && mX <= (x + width) && mY >= y && mY <= (y + height));
    }

    public Snake getSnake() {
        return snake;
    }
}