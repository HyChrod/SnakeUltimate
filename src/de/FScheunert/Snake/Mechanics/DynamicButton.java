package de.FScheunert.Snake.Mechanics;

import de.FScheunert.Snake.Snake;
import de.FScheunert.Snake.Utilities.DynAction;
import de.FScheunert.Snake.Utilities.GameState;
import org.jetbrains.annotations.NotNull;

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
    public void render(@NotNull Graphics g) {
        g.setColor(getColor());
        int[] dimensions = getDrawingDimensions();
        g.fillRect(dimensions[0], dimensions[1], dimensions[2], dimensions[3]);
        g.setColor(Color.BLACK);
        g.drawRect(dimensions[0], dimensions[1], dimensions[2], dimensions[3]);

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

    private int[] getDrawingDimensions() {
        return new int[] {
                calculateDimension(getPosX(), 20, false),
                calculateDimension(getPosY(), 10, false),
                calculateDimension(getWidth(), 40, true),
                calculateDimension(getHeight(), 20, true)
        };
    }

    private int calculateDimension(int pos, int multiplier, boolean addition) {
        return (int) (pos + ((isHovered() ? (multiplier*getSnake().SCREEN_SIZE_RATIO) : 0) * (addition ? 1 : -1)));
    }

}
