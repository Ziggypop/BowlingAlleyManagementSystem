package Simulation;

import Lanes.Lane;
import gui.EndGamePrompt;

import java.util.Iterator;
import java.util.Vector;

public class Game {

    private Bowler currentThrower;			// = the thrower who just took a throw

    private int ball;

    private GameState theState;

    private Lane onThisLane;

    public Game(Lane lane) {
        onThisLane = lane;
    }

    private boolean began = false;

    private Iterator bowlerIterator;

    /**
     * Begins the game
     * @throws Exception cannot begin a game more than once
     */
    public void begin() throws Exception {
        if (began) {
            throw new Exception("Game can't start twice!");
        }
        theState = new PlayingGameState(this);
        setBowlerIterator(onThisLane.party.getMembers().iterator());
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
        } else if (choice == 2) {// no, dont want to play another game
            Vector printVector;
            gui.EndGameReport egr = new gui.EndGameReport(
                    onThisLane.party.getMembers().get(0).getNickName() + "'s Simulation.Party",
                    onThisLane.party
            );
            printVector = egr.getResult();
            Iterator scoreIt = onThisLane.party.getMembers().iterator();
            onThisLane.party = null;

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

    public int getBall() {
        return ball;
    }

    protected void setBall(int ball) {
        this.ball = ball;
    }

    public Bowler getCurrentThrower() {
        return currentThrower;
    }

    protected void setCurrentThrower(Bowler currentThrower) {
        this.currentThrower = currentThrower;
    }

    public Iterator getBowlerIterator() {
        return bowlerIterator;
    }

    protected void setBowlerIterator(Iterator bowlerIterator) {
        this.bowlerIterator = bowlerIterator;
    }
}
