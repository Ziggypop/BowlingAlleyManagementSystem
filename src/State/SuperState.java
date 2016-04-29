package State;
import State.BowlingFrame.RollEnum;
import static State.BowlingFrame.RollEnum.First;
import static State.BowlingFrame.RollEnum.Second;
import static State.BowlingFrame.RollEnum.Third;

/**
 * Created by ziggypop on 4/29/16.
 *
 */
public abstract class SuperState {
    public static final int MAX_FRAME_SCORE = 10;

    private StateContext context;
    BowlingFrame previousFrame;
    BowlingFrame twicePreviousFrame;


    public SuperState(StateContext context, BowlingFrame prevFrame, BowlingFrame twicePrevFrame){
        this.context = context;
        this.previousFrame = prevFrame;
        this.twicePreviousFrame = twicePrevFrame;
    }

    /**
     * TODO: Account for the 10th frame
     * Calculates the value of a current frame and updates the previous frames based on the current state.
     * @param frame The frame from which the rolls are extracted.
     * @return The _CURRENT_ value of the Frame between 0 and 10. This is not the final value of the frame
     *          (Which has a range of 0-30)
     */
    public int evaluateFrame(BowlingFrame frame){
        // Get the rolls from the frame.
        int roll1 = unwrapRollOrZero(frame, First);
        int roll2 = unwrapRollOrZero(frame, Second);
        int roll3 = unwrapRollOrZero(frame, Third);

        // If it was a strike
        if (roll1 == MAX_FRAME_SCORE ){
            //if this state is in a strike state
            if (this instanceof DoubleStrikeState || this instanceof StrikeState) {
                //Then enter into a DoubleStrikeState.
                context.setState(new DoubleStrikeState(context, frame, previousFrame));
            } else {
                //Enter into a StrikeState otherwise.
                context.setState(new StrikeState(context, frame));
            }
            frame.addToFrameScore(roll1);
            updatePrevScoreFromFirstRoll(roll1);
            return roll1;
        }
        // If it was a spare
        else if (roll1 + roll2 == MAX_FRAME_SCORE){
            //Enter into a SpareState
            context.setState(new SpareState(context, frame));
            frame.addToFrameScore(roll1+roll2);
            updatePrevScoreFromFirstRoll(roll1);
            updatePrevScoreFromSecondRoll(roll2);
            return roll1 + roll2;
        }
        //The frame was normal (0-9)
        else {
            context.setState(new NormalState(context));
            //Set this frame's score.
            frame.addToFrameScore(roll1 + roll2 + roll3);
            return roll1 + roll2 + roll3;
        }
    }

    /**
     * Defines what should happen when you want to update the previous score for the first roll.
     * @param scoreToAdd The score to add.
     */
    public abstract void updatePrevScoreFromFirstRoll(int scoreToAdd);
    /**
     * Defines what should happen when you want to update the previous score for the second roll.
     * @param scoreToAdd The score to add.
     */
    public abstract void updatePrevScoreFromSecondRoll(int scoreToAdd);


    /**
     * Helper method that locates the ugly try/catch logic outside of the evaluateFrame method.
     * @param frame The frame from which the roll shall be extracted.
     * @param roll An enum of the Roll you want.
     * @return An int representing the roll, or 0 if it does not exist.
     */
    private int unwrapRollOrZero(BowlingFrame frame, BowlingFrame.RollEnum roll) {
        try {
            switch (roll) {
                case First:
                    return frame.getRoll1();
                case Second:
                    return frame.getRoll2();
                case Third:
                    return frame.getRoll3();
                default:
                    return 0;
            }
        } catch (BowlingFrame.RollException e) {
            return 0;
        }
    }




}
