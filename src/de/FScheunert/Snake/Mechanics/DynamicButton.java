package de.FScheunert.Snake.Mechanics;

import de.FScheunert.Snake.Snake;
import de.FScheunert.Snake.Utilities.DynAction;
import de.FScheunert.Snake.Utilities.GameState;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class DynamicButton extends DynamicElement {

    private final String text;
    private final Color color;

    private DynAction action = null;

    private boolean hovered = false;

    public DynamicButton(double relX, double relY, double relWidth, double relHeight, Color color, String text,
                         GameState state, Snake snake) {
        super(relX, relY, relWidth, relHeight, snake, state);
        this.text = text;
        this.color = color;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(getColor());
        g.fillRect((int) (getPosX() - (isHovered() ? (20*getSnake().SCREEN_SIZE_RATIO) : 0)),
                (int) (getPosY() - (isHovered() ? (10*getSnake().SCREEN_SIZE_RATIO) : 0)),
                (int) (getWidth() + (isHovered() ? (40*getSnake().SCREEN_SIZE_RATIO) : 0)),
                (int) (getHeight() + (isHovered() ? (20*getSnake().SCREEN_SIZE_RATIO) : 0)));
        g.setColor(Color.BLACK);
        g.drawRect((int) (getPosX() - (isHovered() ? (20*getSnake().SCREEN_SIZE_RATIO) : 0)),
                (int) (getPosY() - (isHovered() ? (10*getSnake().SCREEN_SIZE_RATIO) : 0)),
                (int) (getWidth() + (isHovered() ? (40*getSnake().SCREEN_SIZE_RATIO) : 0)),
                (int) (getHeight() + (isHovered() ? (20*getSnake().SCREEN_SIZE_RATIO) : 0)));

        Rectangle2D textMessurements = g.getFontMetrics().getStringBounds(getText(), g);
        g.drawString(getText(), (int) (getSnake().getFrameBuilder().getWidth()*getRelX() - textMessurements.getWidth()/2),
                (int) (getSnake().getFrameBuilder().getHeight()*getRelY() + textMessurements.getHeight()/2));
    }

    @Override
    public void tick() {}

    public void setAction(DynAction action) {
        this.action = action;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public boolean isHovered() {
        return hovered;
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }

    public DynAction getAction() {
        return action;
    }

    public void interact() {
        if(getAction() != null) getAction().interact();
        setHovered(false);
    }

}
