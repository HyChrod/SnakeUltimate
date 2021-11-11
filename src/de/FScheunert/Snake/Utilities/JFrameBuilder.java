package de.FScheunert.Snake.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class JFrameBuilder {

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private JFrame frame = null;

    private int width = 0, height = 0;
    private ScreenPosition position = ScreenPosition.CENTER;

    public JFrameBuilder() {
        this("");
    }

    public JFrameBuilder(String title) {
        this.frame = new JFrame(title);
    }

    public JFrameBuilder setTitle(String title) {
        this.frame.setTitle(title);
        return this;
    }

    public JFrameBuilder setSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.frame.setSize(new Dimension(width, height));
        return this;
    }

    public JFrameBuilder setSize(double percentageRatio) {
        setSize((int)(SCREEN_SIZE.width*percentageRatio), (int)(SCREEN_SIZE.height*percentageRatio));
        return this;
    }

    public JFrameBuilder setPosition(ScreenPosition position) {
        this.position = position;
        return this;
    }

    public JFrameBuilder setUndecorated(boolean value) {
        this.frame.setUndecorated(value);
        return this;
    }

    public JFrameBuilder setResizable(boolean value) {
        this.frame.setResizable(value);
        return this;
    }

    public JFrameBuilder addKeyListener(KeyListener listener) {
        this.frame.addKeyListener(listener);
        return this;
    }

    public JFrameBuilder add(Component component) {
        this.frame.add(component);
        return this;
    }

    public JFrameBuilder build() {
        this.frame.setBounds(position.calculatePosX(getWidth()), position.calculatePosY(getHeight()),
                getWidth(), getHeight());
        this.frame.setFocusable(true);
        this.frame.setVisible(true);
        return this;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ScreenPosition getPosition() {
        return position;
    }

    public enum ScreenPosition {

        CENTER(0.5,0.5, -2, -2),
        CENTER_LEFT(1,0.5, 0, -2),
        CENTER_RIGHT(0, 0.5, -1, -2),
        CENTER_TOP(0.5, 1, -2, 0),
        CENTER_BOTTOM(0.5, 0, -2, -1),
        TOP_LEFT(1, 1, 0, 0),
        TOP_RIGHT(0, 1, -1, 0),
        BOTTOM_LEFT(1, 0, 0, -1),
        BOTTOM_RIGHT(0, 0, -1, -1);

        private double widthDisplacement;
        private double heightDisplacement;

        private int widthSizeFactor;
        private int heightSizeFactor;

        ScreenPosition(double widthDisplacement, double heightDisplacement, int widthFactor, int heightFactor) {
            this.widthDisplacement = widthDisplacement;
            this.heightDisplacement = heightDisplacement;
            this.widthSizeFactor = widthFactor;
            this.heightSizeFactor = heightFactor;
        }

        public int calculatePosX(int width) {
            int posX = (int) (SCREEN_SIZE.width - (SCREEN_SIZE.width*getWidthDisplacement()));
            return widthSizeFactor != 0 ? posX + width/getWidthSizeFactor() : posX;
        }

        public int calculatePosY(int height) {
            int posY = (int) (SCREEN_SIZE.height - (SCREEN_SIZE.height*getHeightDisplacement()));
            return heightSizeFactor != 0 ? posY + height/getHeightSizeFactor() : posY;
        }

        public double getWidthDisplacement() {
            return widthDisplacement;
        }

        public double getHeightDisplacement() {
            return heightDisplacement;
        }

        public int getHeightSizeFactor() {
            return heightSizeFactor;
        }

        public int getWidthSizeFactor() {
            return widthSizeFactor;
        }
    }

}
