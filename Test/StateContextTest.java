
import State.BowlingFrame;
import State.StateContext;
import junit.framework.TestCase;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by ziggypop on 4/30/16.
 */
public class StateContextTest {
    @Test
    public void calculateTotal() throws Exception {
        ArrayList<BowlingFrame> frames = new ArrayList<>();
        StateContext context = new StateContext(frames);
        Assert.assertTrue(context.calculateTotal() == 0);

        BowlingFrame firstFrame = new BowlingFrame(false);
        firstFrame.addRoll(5);
        firstFrame.addRoll(3);
        frames.add(firstFrame);

        context = new StateContext(frames);
        int total = context.calculateTotal();
        Assert.assertEquals(total, 8);


    }
    @Test
    public void calculateTotalMany() throws Exception {
        ArrayList<BowlingFrame> frames = new ArrayList<>();
        StateContext context = new StateContext(frames);

        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(5,0));
        frames.add(frameFactory(0,0));

        int total = context.calculateTotal();
        Assert.assertEquals(total, 45);
    }
    @Test
    public void calcualtePerfectGame() throws Exception{
        ArrayList<BowlingFrame> frames = new ArrayList<>();
        StateContext context = new StateContext(frames);

        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        BowlingFrame lastFrame = new BowlingFrame(true);
        lastFrame.addRoll(10);
        lastFrame.addRoll(10);
        frames.add(lastFrame);

        int total = context.calculateTotal();
        System.out.println(total);
        Assert.assertEquals(total, 300);

    }

    @Test
    public void calculateNinePerfectLanes() throws Exception {
        ArrayList<BowlingFrame> frames = new ArrayList<>();
        StateContext context = new StateContext(frames);

        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));
        frames.add(frameFactory(10,0));

        int total = context.calculateTotal();
        Assert.assertEquals(total, 240);
    }


    //helper
    private BowlingFrame frameFactory(int first, int second) throws BowlingFrame.FrameException {
        BowlingFrame frame = new BowlingFrame(false);
        frame.addRoll(first);
        if (second + first <=10){
            frame.addRoll(second);
        }
        return frame;
    }

}