import State.BowlingFrame;
import org.junit.Assert;
import org.junit.Test;
import Lanes.Lane;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by ziggypop on 5/5/16.
 */
public class LaneTest {

    @Test
    public void formatScoresToFramesTestSimple() throws BowlingFrame.RollException {
        int[] scores = new int[21];
        scores[0] = 5;
        scores[1] = 4;



        ArrayList<BowlingFrame> frames = Lane.formatScoresToFrames(scores);

        int frame1Roll1 = frames.get(0).getRoll1();
        int frame1Roll2 = frames.get(0).getRoll2();

        Assert.assertEquals(scores[0], frame1Roll1);
        Assert.assertEquals(scores[1], frame1Roll2);

        Assert.assertEquals(frames.size(), 1);

    }

    @Test
    public void formatScoresToFramesTestLast() throws BowlingFrame.RollException {
        int[] scores = new int[21];
        for (int i = 0; i < 21; i++) {
            scores[i] = 0;
        }
        scores[0] = 3;
        scores[18] = 10;
        scores[19] = 10;
        scores[20] = 5;


        ArrayList<BowlingFrame> frames = Lane.formatScoresToFrames(scores);

        System.out.println("number of frames: " + frames.size());

        int count = 0;
        for (BowlingFrame frame : frames){

            System.out.println("Frame "+ count +": " + frame.toString());
            count++;
        }

        int frameFirstRoll1 = frames.get(0).getRoll1();

        int frameLastRoll1 = frames.get(9).getRoll1();
        int frameLastRoll2 = frames.get(9).getRoll2();
        int frameLastRoll3 = frames.get(9).getRoll3();

        Assert.assertEquals(scores[0], frameFirstRoll1);
        Assert.assertEquals(scores[18], frameLastRoll1);
        Assert.assertEquals(scores[19], frameLastRoll2);
        Assert.assertEquals(scores[20], frameLastRoll3);

        Assert.assertEquals(frames.size(), 10);

    }



}