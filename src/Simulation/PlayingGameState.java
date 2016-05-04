package Simulation;

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
        if (theGame.getLane().bowlerIterator.hasNext()) {
            theGame.getLane().currentThrower = (Bowler)theGame.getLane().bowlerIterator.next();

            theGame.getLane().canThrowAgain = true;
            theGame.getLane().tenthFrameStrike = false;
            theGame.getLane().ball = 0;
            while (theGame.getLane().canThrowAgain) {
                theGame.getLane().setter.ballThrown();		// simulate the thrower's ball hiting
                theGame.getLane().ball++;
            }

            if (theGame.getLane().frameNumber == 9){
                theGame.getLane().finalScores[theGame.getLane().bowlIndex][theGame.getLane().gameNumber] = theGame.getLane().cumulScores[theGame.getLane().bowlIndex][9];
                try{
                    Date date = new Date();
                    String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
                    Scores.ScoreHistoryFile.addScore(theGame.getLane().currentThrower.getNick(), dateString, new Integer(theGame.getLane().cumulScores[theGame.getLane().bowlIndex][9]).toString());
                } catch (Exception e) {System.err.println("Exception in addScore. "+ e );}
            }


            theGame.getLane().setter.reset();
            theGame.getLane().bowlIndex++;

        } else {
            theGame.getLane().frameNumber++;
            theGame.getLane().resetBowlerIterator();
            theGame.getLane().bowlIndex = 0;
            if (theGame.getLane().frameNumber > 9) {
                theGame.getLane().gameNumber++;
            }
        }
    }
}