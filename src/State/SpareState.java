package State;

/**
 * Created by ziggypop on 4/29/16.
 */
public class SpareState extends SuperState {

    public SpareState(ScoreCalculatingStateContext context, BowlingFrame prevFrame){
        super(context, prevFrame, null);
    }

    @Override
    public void updatePrevScoreFromFirstRoll(int scoreToAdd) {
        //The first roll should factor into the previous frame
        previousFrame.addToFrameScore(scoreToAdd);
    }

    @Override
    public void updatePrevScoreFromSecondRoll(int scoreToAdd) {
        //Do nothing, this is a spare, the second roll shall not do anything
    }
}
