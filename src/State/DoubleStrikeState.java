package State;

/**
 * Created by ziggypop on 4/29/16.
 * This state represents the state when your last two Frames have been strikes.
 */
public class DoubleStrikeState extends SuperState {

    public DoubleStrikeState(StateContext context, BowlingFrame prevFrame, BowlingFrame twicePrevFrame) {
        super(context, prevFrame, twicePrevFrame);
    }

    @Override
    public void updatePrevScoreFromFirstRoll(int scoreToAdd) {
        previousFrame.addToFrameScore(scoreToAdd);
        twicePreviousFrame.addToFrameScore(scoreToAdd);
    }

    @Override
    public void updatePrevScoreFromSecondRoll(int scoreToAdd) {
        previousFrame.addToFrameScore(scoreToAdd);
    }
}
