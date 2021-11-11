package de.FScheunert.Snake;

import de.FScheunert.Snake.Entitys.Apple;
import de.FScheunert.Snake.Entitys.Head;
import de.FScheunert.Snake.Mechanics.EntityHandler;
import de.FScheunert.Snake.Mechanics.KeyboardListener;
import de.FScheunert.Snake.Utilities.Heartbeat;
import de.FScheunert.Snake.Utilities.JFrameBuilder;

import javax.tools.Tool;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.security.Key;

public class Snake extends Canvas {

    public static void main(String[] args) {
        new Snake();
    }

    private final double SCREEN_SIZE_RATIO = 0.75;
    public final int SQUARE_INDEX_WIDTH = 15;
    public final int SQUARE_INDEX_HEIGHT;
    private final double SCREEN_FACTOR;
    public final int WIDTH_FACTOR;
    public final int HEIGHT_FACTOR;
    public final int ENTITY_MAX_X;
    public final int ENTITY_MAX_Y;
    public final boolean BORDER_OVERPASS = true;

    private JFrameBuilder frameBuilder;

    private EntityHandler entityHandler;

    private Head snakeHead;
    private Apple apple;

    public Snake() {
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH_FACTOR = (int) (screenDimension.getWidth()*SCREEN_SIZE_RATIO / SQUARE_INDEX_WIDTH);
        SCREEN_FACTOR = (screenDimension.getHeight()*SCREEN_SIZE_RATIO)/(screenDimension.getWidth()*SCREEN_SIZE_RATIO);
        SQUARE_INDEX_HEIGHT = (int) (SQUARE_INDEX_WIDTH * SCREEN_FACTOR);
        HEIGHT_FACTOR = (int) (screenDimension.getHeight()*SCREEN_SIZE_RATIO / SQUARE_INDEX_HEIGHT);
        ENTITY_MAX_X = WIDTH_FACTOR*(SQUARE_INDEX_WIDTH-1);
        ENTITY_MAX_Y = HEIGHT_FACTOR*(SQUARE_INDEX_HEIGHT-1);
        initialize();
    }

    private void initialize() {
        this.frameBuilder = new JFrameBuilder("SnakeUltimate by Florian Scheunert")
                .setUndecorated(true)
                .setResizable(false)
                .setSize(SQUARE_INDEX_WIDTH * WIDTH_FACTOR, SQUARE_INDEX_HEIGHT * HEIGHT_FACTOR)
                .setPosition(JFrameBuilder.ScreenPosition.CENTER)
                .add(this)
                .build();
        this.addKeyListener(new KeyboardListener(this));

        new Heartbeat(this, "renderGraphics", 20L);
        new Heartbeat(this, "tickLogic", 200L);

        this.entityHandler = new EntityHandler(this,3);

        this.snakeHead = new Head(5*WIDTH_FACTOR, 3*HEIGHT_FACTOR, Color.GREEN,this);
        this.apple = new Apple(7*WIDTH_FACTOR, 3*HEIGHT_FACTOR, Color.RED, this);
    }

    long lastExecution = System.currentTimeMillis();
    public synchronized void renderGraphics() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
        requestFocus();
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0,0, getFrameBuilder().getWidth(), getFrameBuilder().getHeight());

        getEntityHandler().render(g);

        g.setColor(Color.BLACK);
        for(int x = 1; x < SQUARE_INDEX_WIDTH+1; x++) {
            g.drawRect(WIDTH_FACTOR*x, 0, 0, getFrameBuilder().getHeight());
            if(x < SQUARE_INDEX_HEIGHT)
                g.drawRect(0, HEIGHT_FACTOR*x, getFrameBuilder().getWidth(), 0);
        }

        g.dispose();
        bs.show();

        System.out.println("Delay: " + (System.currentTimeMillis()-lastExecution));
        lastExecution = System.currentTimeMillis();
    }

    public synchronized  void tickLogic() {
        getEntityHandler().tick();
    }

    public JFrameBuilder getFrameBuilder() {
        return frameBuilder;
    }

    public EntityHandler getEntityHandler() {
        return entityHandler;
    }

    public Head getSnakeHead() {
        return this.snakeHead;
    }

    public Apple getApple() {
        return this.apple;
    }

}
