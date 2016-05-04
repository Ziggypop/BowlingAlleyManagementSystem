package Simulation;

import Lanes.Lane;
import gui.EndGamePrompt;

import java.util.Iterator;
import java.util.Vector;

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
     * @return the lane this game is being played on
     */
    public Lane getLane() {
        return onThisLane;
    }

    /**
     * The state of the game before it was paused, null if hasn't been paused
     */
    private GameState prePause;

    /**
     * Halts the game
     */
    public void pause() {
        prePause = theState;
        setState(new HaltedGameState(this));
    }

    /**
     * Unpause the game
     * @throws Exception if the game isn't paused
     */
    public void unpause() throws Exception {
        if (prePause == null) {
            throw new Exception("Can't unpause what hasn't been paused");
        }
        setState(prePause);
        prePause = null;
    }

    /**
     * Ends the Game
     */
    public void end() {
        EndGamePrompt egp = new EndGamePrompt(onThisLane.party.getMembers().get(0).getNickName() + "'s Party");
        int choice = egp.getResult();
        egp.distroy();
        if (choice == 1) {					// yes, want to play again
            onThisLane.resetScores();
            onThisLane.resetBowlerIterator();
        } else if (choice == 2) {// no, dont want to play another game
            Vector printVector;
            gui.EndGameReport egr = new gui.EndGameReport(
                    onThisLane.party.getMembers().get(0).getNickName() + "'s Simulation.Party",
                    onThisLane.party
            );
            printVector = egr.getResult();
            onThisLane.partyAssigned = false;
            Iterator scoreIt = onThisLane.party.getMembers().iterator();
            onThisLane.party = null;
            onThisLane.partyAssigned = false;

            onThisLane.publish(onThisLane.lanePublish());

            int myIndex = 0;
            while (scoreIt.hasNext()){
                Simulation.Bowler thisBowler = (Simulation.Bowler)scoreIt.next();
                Scores.ScoreReport sr = new Scores.ScoreReport( thisBowler, onThisLane.finalScores[myIndex++], onThisLane.gameNumber );
                sr.sendEmail(thisBowler.getEmail());
                Iterator printIt = printVector.iterator();
                while (printIt.hasNext()){
                    if (thisBowler.getNick() == (String)printIt.next()){
                        System.out.println("Printing " + thisBowler.getNick());
                        sr.sendPrintout();
                    }
                }

            }
        }
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
