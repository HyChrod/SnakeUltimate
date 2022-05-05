package de.FScheunert.Snake;

import de.FScheunert.Snake.Entitys.Apple;
import de.FScheunert.Snake.Entitys.Head;
import de.FScheunert.Snake.Mechanics.*;
import de.FScheunert.Snake.Utilities.GameState;
import de.FScheunert.Snake.Utilities.Heartbeat;
import de.FScheunert.Snake.Utilities.JFrameBuilder;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Snake extends Canvas {

    public static void main(String[] args) {}

    private static final Snake INSTANCE = new Snake();
    public static Snake getInstance() {return INSTANCE;}

    // Configuration values
    public final double SCREEN_SIZE_RATIO = 0.5;
    public final int SQUARE_INDEX_WIDTH = 30;
    public final boolean BORDER_OVERPASS = true;

    // Do not change - will be calculated depending on the configuration values
    public final int SQUARE_INDEX_HEIGHT;
    public final int WIDTH_FACTOR;
    public final int HEIGHT_FACTOR;
    public final int ENTITY_MAX_X;
    public final int ENTITY_MAX_Y;

    private JFrameBuilder frameBuilder;

    private EntityHandler entityHandler;

    private DynamicHandler dynamicHandler;

    private Head snakeHead;
    private Apple apple;

    public Snake() {
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH_FACTOR = (int) (screenDimension.getWidth()*SCREEN_SIZE_RATIO / SQUARE_INDEX_WIDTH);
        double SCREEN_FACTOR = (screenDimension.getHeight() * SCREEN_SIZE_RATIO) / (screenDimension.getWidth() * SCREEN_SIZE_RATIO);
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

        MouseListeners mouseListeners = new MouseListeners(this);
        this.addMouseMotionListener(mouseListeners);
        this.addMouseListener(mouseListeners);

        new Heartbeat(this, "renderGraphics", 20L);
        new Heartbeat(this, "tickLogic", 200L);

        this.entityHandler = new EntityHandler(this,3);
        this.dynamicHandler = new DynamicHandler(this);

        this.snakeHead = new Head(WIDTH_FACTOR, 3*HEIGHT_FACTOR, Color.GREEN,this);
        this.apple = new Apple(7*WIDTH_FACTOR, 3*HEIGHT_FACTOR, Color.RED, this);

        //Buttons and DynamicElements
        new DynamicButton(0.5, 0.35, 0.2, 0.1, Color.CYAN, "Resume", GameState.PAUSE, this)
                .setAction(GameState.INGAME::setActive);
        new DynamicButton(0.5, 0.5, 0.2, 0.1, Color.CYAN, "New Game", GameState.PAUSE, this)
                .setAction(this::resetGame);
        new DynamicButton(0.5, 0.65, 0.2, 0.1, Color.CYAN, "Exit", GameState.PAUSE, this)
                .setAction(() -> {
                    resetGame();
                    GameState.MENU.setActive();
                });

        new DynamicButton(0.5, 0.4, 0.2, 0.1, Color.CYAN, "New Game", GameState.END, this)
                .setAction(this::resetGame);
        new DynamicButton(0.5, 0.55, 0.2, 0.1, Color.CYAN, "Exit", GameState.END, this)
                .setAction(() -> {
                    resetGame();
                    GameState.MENU.setActive();
                });

        new DynamicButton(0.5, 0.35, 0.2, 0.1, Color.CYAN, "New Game", GameState.MENU, this)
                .setAction(this::resetGame);
        new DynamicButton(0.5, 0.5, 0.2, 0.1, Color.CYAN, "Settings", GameState.MENU, this);
        new DynamicButton(0.5, 0.65, 0.2, 0.1, Color.CYAN, "Quit Game", GameState.MENU, this)
                .setAction(() -> System.exit(0));
    }

    private void resetGame() {
        getSnakeHead().setPosX(WIDTH_FACTOR);
        getSnakeHead().setPosY(3*HEIGHT_FACTOR);
        getSnakeHead().setDirection(2, true);
        getApple().setPosX(7*WIDTH_FACTOR);
        getApple().setPosY(3*HEIGHT_FACTOR);

        getEntityHandler().reset();
        GameState.INGAME.setActive();
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

        Font f = new Font(Font.SANS_SERIF, Font.BOLD, (int) (25 * SCREEN_SIZE_RATIO));
        g.setFont(f);

        getEntityHandler().render(g);

        g.setColor(Color.BLACK);
        setTransparency(g, 0.2F);
        for(int x = 1; x < SQUARE_INDEX_WIDTH+1; x++) {
            g.drawRect(WIDTH_FACTOR*x, 0, 0, getFrameBuilder().getHeight());
            if(x < SQUARE_INDEX_HEIGHT)
                g.drawRect(0, HEIGHT_FACTOR*x, getFrameBuilder().getWidth(), 0);
        }
        setTransparency(g, 1);

        if(!BORDER_OVERPASS) {
            g.setColor(Color.RED);
            g.fillRect(0, 0, getFrameBuilder().getWidth(), 4);
            g.fillRect(0, 0, 4, getFrameBuilder().getHeight());
            g.fillRect(getFrameBuilder().getWidth()-4, 0, 4, getFrameBuilder().getHeight());
            g.fillRect(0, getFrameBuilder().getHeight()-4, getFrameBuilder().getWidth(), 4);
        }

        switch(GameState.getGameState()) {
            case PAUSE -> renderPause(g);
            case MENU -> renderMenu(g);
            case END -> renderEnd(g);
        }
        if(!GameState.INGAME.isActive()) getDynamicHandler().render(g);

        g.dispose();
        bs.show();

        //System.out.println("Delay: " + (System.currentTimeMillis()-lastExecution));
        lastExecution = System.currentTimeMillis();
    }

    private void renderPause(Graphics g) {
        renderBaseOverlay(g, Color.LIGHT_GRAY);
    }

    private void renderEnd(Graphics g) {
        renderBaseOverlay(g, Color.RED);
    }

    private void renderMenu(Graphics g) {
        renderBaseOverlay(g, Color.CYAN);
    }

    private void renderBaseOverlay(Graphics g, Color background) {
        setTransparency(g, 0.5F);
        g.setColor(background);
        g.fillRect(0, 0, getFrameBuilder().getWidth(), getFrameBuilder().getHeight());
        setTransparency(g, 1);

        g.setColor(Color.WHITE);
        int menuWidth = (int) (getFrameBuilder().getWidth()*0.3);
        int menuHeight = (int) (getFrameBuilder().getHeight()*0.75);
        g.fillRect(getFrameBuilder().getWidth()/2 - menuWidth/2, getFrameBuilder().getHeight()/2 - menuHeight/2,
                menuWidth, menuHeight);
        g.setColor(Color.BLACK);
        g.drawRect(getFrameBuilder().getWidth()/2 - menuWidth/2, getFrameBuilder().getHeight()/2 - menuHeight/2,
                menuWidth, menuHeight);
    }

    private void setTransparency(Graphics g, float value) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, value));
    }

    public synchronized  void tickLogic() {
        if(GameState.INGAME.isActive()) getEntityHandler().tick();
        else getDynamicHandler().tick();
    }

    public JFrameBuilder getFrameBuilder() {
        return frameBuilder;
    }

    public EntityHandler getEntityHandler() {
        return entityHandler;
    }

    public DynamicHandler getDynamicHandler() {
        return dynamicHandler;
    }

    public Head getSnakeHead() {
        return this.snakeHead;
    }

    public Apple getApple() {
        return this.apple;
    }

}