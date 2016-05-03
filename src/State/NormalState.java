package State;

/**
 * Created by ziggypop on 4/29/16.
 */
public class NormalState extends SuperState {
    public NormalState(ScoreCalculatingStateContext context) {
        super(context, null, null);
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
