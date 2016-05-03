package State;

import java.util.ArrayList;

/**
 * Created by ziggypop on 4/29/16.
 * The state Context will be created with an Arraylist of Bowling frames.
 * These frames should belong to a player.
 * Running calculateTotal, will run all of the frames through a state machine,
 * updating all of the totals.
 * CalculateTotal() will return a total of all frames.
 *
 * This State machine is not long running; one will be spawned for each set of
 * frames to be evaluated.
 *
 */
public class ScoreCalculatingStateContext {

    private SuperState state;
    private ArrayList<BowlingFrame> frames;

    /**
     *
     * @param frames The frames you wish to evaluate.
     */
    public ScoreCalculatingStateContext(ArrayList<BowlingFrame> frames){
        state = new NormalState(this);
        this.frames = frames;
    }

    public void setState(SuperState newState){
        state = newState;
    }

    /**
     * Calculates the total of all of the current frames.
     * @return A total of all of the BowlingFrames, with the modifications to addition applied by the state machine.
     */
    public int calculateTotal(){
        setState(new NormalState(this));
        int total = 0;
        //Iterate through all of the frames, passing each frame into the currently defined state.
        for (BowlingFrame frame : frames){
            state.evaluateFrame(frame);
        }
        //Iterate through all of the frames, summing the scores from each frame.
        for (BowlingFrame frame : frames){
            total += frame.getFrameScore();
        }

        return total;
    }

}
