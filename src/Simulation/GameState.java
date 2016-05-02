package Simulation;

/**
 * Created by byronzaharako on 5/2/16.
 */
public abstract class GameState {

    protected Game theGame;

    public GameState(Game game) {
        theGame = game;
    }

    public abstract boolean hasNextTurn();

    public abstract void nextTurn();

}
