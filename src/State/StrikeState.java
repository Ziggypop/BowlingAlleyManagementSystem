package State;

/**
 * Created by ziggypop on 4/29/16.
 * Because your previous Frame was a Strike, you must update it again
 */
public class StrikeState extends SuperState {

    public StrikeState(StateContext context, BowlingFrame prevFrame){
        super(context, prevFrame, null);    }


    @Override
    public void updatePrevScoreFromFirstRoll(int scoreToAdd) {
        previousFrame.addToFrameScore(scoreToAdd);
    }

    @Override
    public void updatePrevScoreFromSecondRoll(int scoreToAdd) {
        twicePreviousFrame.addToFrameScore(scoreToAdd);
    }
}
