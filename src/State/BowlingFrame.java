package State;


import java.util.Optional;

/**
 * Created by ziggypop on 4/29/16.
 */
public class BowlingFrame {

    private Integer roll1;
    private Integer roll2;
    private Integer roll3;
    private boolean isLastFrame;
    private Integer frameScore;


    public BowlingFrame(boolean isLastFrame){
        frameScore = 0;
        roll1 = null;
        roll2 = null;
        this.isLastFrame = isLastFrame;

    }

    public void addRoll(int newRoll) throws FrameException{
        if (roll1 == null){
            roll1 = (Integer) newRoll;
        } else if (roll2 == null){
            roll2 = (Integer) newRoll;
        } else if (isLastFrame){
            roll3 = (Integer) newRoll;
        } else {
            throw new FrameException("The Frame is Full");
        }
    }

    public int getRoll1() throws RollException{
        if (roll2 != null){
            return roll1;
        } else {
            throw new RollException("This is not the Roll you are looking for...");
        }
    }

    public int getRoll2() throws RollException{
        if (roll2 != null){
            return roll2;
        } else {
            throw new RollException("This is not the Roll you are looking for...");
        }    }

    public int getRoll3() throws RollException{
        if (roll3 != null){
            return roll3;
        } else {
            throw new RollException("This is not the Roll you are looking for...");
        }
    }

    public void addToFrameScore(int score){
        frameScore += score;
    }
    public int getFrameScore(){
        return frameScore;
    }

    public boolean isNotLast(){
        return !isLastFrame;
    }

    public class FrameException extends Exception{
        public FrameException(String msg){
            super(msg);
        }

    }

    public class RollException extends Exception{
        public RollException(String msg){
            super(msg);
        }
    }

    public enum RollEnum{
        First,
        Second,
        Third
    }

    public String toString(){
        return String.format("roll1: %d, + roll2: %d, +roll3: %d, framescore=%d", roll1, roll2, roll3, frameScore);
    }
}
