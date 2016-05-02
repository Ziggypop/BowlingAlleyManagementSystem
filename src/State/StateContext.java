package State;

import java.util.ArrayList;

/**
 * Created by ziggypop on 4/29/16.
 */
public class StateContext {
    private SuperState state;
    private ArrayList<BowlingFrame> frames;

    public StateContext(ArrayList<BowlingFrame> frames){
        state = new NormalState(this);
        this.frames = frames;
    }

    public void setState(SuperState newState){
        state = newState;
    }

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
