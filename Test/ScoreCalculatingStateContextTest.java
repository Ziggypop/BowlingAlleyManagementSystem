
import State.BowlingFrame;
import State.ScoreCalculatingStateContext;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by ziggypop on 4/30/16.
 */
public class ScoreCalculatingStateContextTest {

    /**
     * Basic test to see if frames can be created, updated, and calculated correctly.
     * @throws Exception
     */
    @Test
    public void calculateTotal() throws Exception {
        ArrayList<BowlingFrame> frames = new ArrayList<>();
        ScoreCalculatingStateContext context = new ScoreCalculatingStateContext(frames);
        Assert.assertTrue(context.calculateTotal() == 0);

        BowlingFrame firstFrame = new BowlingFrame(false);
        firstFrame.addRoll(5);
        firstFrame.addRoll(3);
        frames.add(firstFrame);

        context = new ScoreCalculatingStateContext(frames);
        int total = context.calculateTotal();
        Assert.assertEquals(total, 8);


    }

    /**
     * Tests a game where the bowler has bowler a few frames (Including strikes)
     * @throws Exception
     */
    @Test
    public void calculateTotalMany() throws Exception {
        ArrayList<BowlingFrame> frames = new ArrayList<>();
        ScoreCalculatingStateContext context = new ScoreCalculatingStateContext(frames);

        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(5,0));
        frames.add(frameFactoryMethod(0,0));

        int total = context.calculateTotal();
        Assert.assertEquals(total, 45);
    }
    /**
     * Tests a game where the bowler has bowled few frames (Strike and spare)
     * @throws Exception
     */
    @Test
    public void calculateTotalManyAltOne() throws Exception {
        ArrayList<BowlingFrame> frames = new ArrayList<>();
        ScoreCalculatingStateContext context = new ScoreCalculatingStateContext(frames);

        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,0));

        int total = context.calculateTotal();
        Assert.assertEquals(total, 40);
    }

    /**
     * A test comprising mostly of spares
     * @throws Exception
     */
    @Test
    public void calculateTotalSparesOne() throws Exception {
        ArrayList<BowlingFrame> frames = new ArrayList<>();
        ScoreCalculatingStateContext context = new ScoreCalculatingStateContext(frames);

        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,0));
        frames.add(frameFactoryMethod(0,0));

        int total = context.calculateTotal();
        Assert.assertEquals(total, 125);
    }

    /**
     * A test comprising mostly of spares.
     * This differs from the first test when the 9th frame has a 0,5 instead of a 5,0
     * This should test for the spares only counting the first roll of the next frame.
     * @throws Exception
     */
    @Test
    public void calculateTotalSparesTwo() throws Exception {
        ArrayList<BowlingFrame> frames = new ArrayList<>();
        ScoreCalculatingStateContext context = new ScoreCalculatingStateContext(frames);

        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(5,5));
        frames.add(frameFactoryMethod(0,5));
        frames.add(frameFactoryMethod(0,0));

        int total = context.calculateTotal();
        Assert.assertEquals(total, 120);
    }

    /**
     * Tests a game where the bowler bowls a perfect game.
     * @throws Exception
     */
    @Test
    public void calculatePerfectGame() throws Exception{
        ArrayList<BowlingFrame> frames = new ArrayList<>();
        ScoreCalculatingStateContext context = new ScoreCalculatingStateContext(frames);

        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        BowlingFrame lastFrame = new BowlingFrame(true);
        lastFrame.addRoll(10);
        lastFrame.addRoll(10);
        lastFrame.addRoll(10);
        frames.add(lastFrame);

        int total = context.calculateTotal();
        Assert.assertEquals(total, 300);
    }

    /**
     * Tests a game where the bowler gets an almost prefect game.
     * @throws Exception
     */
    @Test
    public void calculateAlmostPerfectGame() throws Exception{
        ArrayList<BowlingFrame> frames = new ArrayList<>();
        ScoreCalculatingStateContext context = new ScoreCalculatingStateContext(frames);

        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        BowlingFrame lastFrame = new BowlingFrame(true);
        lastFrame.addRoll(10);
        lastFrame.addRoll(10);
        lastFrame.addRoll(0);
        frames.add(lastFrame);

        int total = context.calculateTotal();
        Assert.assertEquals(total, 290);
    }

    /**
     * Calculate a game where the bowler has bowled 9 perfect lanes.
     * @throws Exception
     */
    @Test
    public void calculateNinePerfectLanes() throws Exception {
        ArrayList<BowlingFrame> frames = new ArrayList<>();
        ScoreCalculatingStateContext context = new ScoreCalculatingStateContext(frames);

        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));
        frames.add(frameFactoryMethod(10,0));

        int total = context.calculateTotal();
        Assert.assertEquals(total, 240);
    }


    /**
     * Helper function that creates a Bowling frame in a simple manner.
     * @param first The first roll
     * @param second The second roll
     * @return A new BowlingFrame.
     * @throws BowlingFrame.FrameException
     */
    private BowlingFrame frameFactoryMethod(int first, int second) throws BowlingFrame.FrameException {
        BowlingFrame frame = new BowlingFrame(false);
        frame.addRoll(first);
        if (second + first <=10){
            frame.addRoll(second);
        }
        return frame;
    }

}