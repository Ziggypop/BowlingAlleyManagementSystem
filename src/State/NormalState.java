package State;

/**
 * Created by ziggypop on 4/29/16.
 */
public class NormalState extends SuperState {
    public NormalState(StateContext context) {
        super(context, null, null);
    }

    public int evaluateFrame(BowlingFrame frame) {
        return super.evaluateFrame(frame);
    }

    @Override
    public void updatePrevScoreFromFirstRoll(int scoreToAdd) {
        //Do nothing, because this is a normal state, there is no responsibility to update anything.
    }

    @Override
    public void updatePrevScoreFromSecondRoll(int scoreToAdd) {
        //Do nothing, because this is a normal state, there is no responsibility to update anything.
    }
}
