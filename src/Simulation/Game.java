package Simulation;

import Lanes.Lane;

public class Game {


    private GameState theState;

    private Lane onThisLane;

    public Game(Lane lane) {
        onThisLane = lane;
    }

    public boolean hasNextTurn() {
        return theState.hasNextTurn();
    }

    public void nextTurn() {
        theState.nextTurn();
    }

    public void setState(GameState state) {
        this.theState = state;
    }

    public GameState getState() {
        return theState;
    }

    /**
     * The state of the game before it was paused
     */
    private GameState prePause;

    public void pause() {
        prePause = theState;
        setState(new HaltedGameState(this));
    }

    public void unpause() throws Exception {
        if (prePause == null) {
            throw new Exception("Can't unpause what hasn't been paused");
        }
        setState(prePause);
        prePause = null;
    }

    /*
        if (bowlerIterator.hasNext()) {
            currentThrower = (Simulation.Bowler)bowlerIterator.next();

            canThrowAgain = true;
            tenthFrameStrike = false;
            ball = 0;
            while (canThrowAgain) {
                setter.ballThrown();		// simulate the thrower's ball hiting
                ball++;
            }

            if (frameNumber == 9){
                finalScores[bowlIndex][gameNumber] = cumulScores[bowlIndex][9];
                try{
                    Date date = new Date();
                    String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
                    Scores.ScoreHistoryFile.addScore(currentThrower.getNick(), dateString, new Integer(cumulScores[bowlIndex][9]).toString());
                } catch (Exception e) {System.err.println("Exception in addScore. "+ e );}
            }


            setter.reset();
            bowlIndex++;

        } else {
            frameNumber++;
            resetBowlerIterator();
            bowlIndex = 0;
            if (frameNumber > 9) {
                gameFinished = true;
                gameNumber++;
            }
        }
    }*/


}
