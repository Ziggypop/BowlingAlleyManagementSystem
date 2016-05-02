package Simulation;

/**
 * Created by byronzaharako on 5/2/16.
 */
public class HaltedGameState extends GameState {

    public HaltedGameState(Game game) {
        super(game);
    }

    @Override
    public boolean hasNextTurn() {
        return true;
    }

    @Override
    public void nextTurn() {
        // Do nothing
    }
}
