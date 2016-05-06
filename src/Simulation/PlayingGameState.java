package Simulation;

import Lanes.Lane;

import java.util.Date;

/**
 * Created by byronzaharako on 5/4/16.
 */
public class PlayingGameState extends GameState {
    public PlayingGameState(Game game) {
        super(game);
    }

    @Override
    public boolean hasNextTurn() {
        return theGame.getLane().frameNumber < 10;
    }

    @Override
    public void nextTurn() {
        Lane lane = theGame.getLane();

        if (theGame.getBowlerIterator().hasNext()) {
            theGame.setCurrentThrower((Bowler)theGame.getBowlerIterator().next());

            lane.canThrowAgain = true;
            lane.tenthFrameStrike = false;
            theGame.setBall(0);
            while (lane.canThrowAgain) {
                lane.setter.ballThrown();		// simulate the thrower's ball hiting
                theGame.setBall(theGame.getBall() + 1);
            }

            if (lane.frameNumber == 9){
                lane.finalScores[lane.bowlIndex][lane.gameNumber] = lane.cumulScores[lane.bowlIndex][9];
                try{
                    Date date = new Date();
                    String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
                    Scores.ScoreHistoryFile.addScore(theGame.getCurrentThrower().getNick(), dateString, new Integer(lane.cumulScores[lane.bowlIndex][9]).toString());
                } catch (Exception e) {System.err.println("Exception in addScore. "+ e );}
            }


            lane.setter.reset();
            lane.bowlIndex++;

        } else {
            lane.frameNumber++;
            theGame.setBowlerIterator(lane.party.getMembers().iterator());
            lane.bowlIndex = 0;
            if (lane.frameNumber > 9) {
                lane.gameNumber++;
            }
        }
    }
}