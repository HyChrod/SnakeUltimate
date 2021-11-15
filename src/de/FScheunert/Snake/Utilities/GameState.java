package de.FScheunert.Snake.Utilities;

public enum GameState {

    MENU(),
    INGAME(),
    PAUSE(),
    SETTINGS(),
    END();

    private static GameState gameState = MENU;

    public static GameState getGameState() {
        return gameState;
    }

    public static void setGameState(GameState state) {
        gameState = state;
    }

    public boolean isActive() {
        return gameState.equals(this);
    }

    public void setActive() {
        gameState = this;
    }

    GameState() {}

}
