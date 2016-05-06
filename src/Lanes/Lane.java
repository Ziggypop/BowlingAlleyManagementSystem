package Lanes;

import Pins.Pinsetter;
import Pins.PinsetterEvent;
import Pins.PinsetterObserver;
import Simulation.Bowler;
import Simulation.Game;
import Simulation.HaltedGameState;
import Simulation.Party;
import State.BowlingFrame;
import State.ScoreCalculatingStateContext;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;

public class Lane extends Thread implements PinsetterObserver {

	private HashMap scores;
	private Vector subscribers;
	private int[] curScores;

	public int[][] cumulScores;
	public boolean canThrowAgain;
	public int[][] finalScores;
	public int gameNumber;
	public Party party;
	public Pinsetter setter;
	public int bowlIndex;
	public int frameNumber;
	public boolean tenthFrameStrike;

    /**
     * The Game currently being played
     */
	private Game currentGame;


	/** Lanes.Lanes()
	 * 
	 * Constructs a new lane and starts its thread
	 * 
	 * pre none
	 * post a new lane has been created and its thered is executing
	 */
	public Lane() { 
		setter = new Pinsetter();
		scores = new HashMap();
		subscribers = new Vector();

		gameNumber = 0;

		setter.subscribe( this );
		
		this.start();
	}

	/** run()
	 * 
	 * entry point for execution of this lane 
	 */
	public void run() {
		while (true) {
			try {
				synchronized (this) {
					wait(); //Thread waits until called to start the game
				}
			} catch (InterruptedException e) {
                System.out.println("Party assigned, Running Lane!");
            }
            currentGame = new Game(this);
			try {
				currentGame.begin();
			} catch (Exception e) {
				e.printStackTrace();
			}
			while (currentGame.hasNextTurn()) {
                currentGame.nextTurn();
            }
			currentGame.end();
		}
	}

	
	/** recievePinsetterEvent()
	 * 
	 * recieves the thrown event from the pinsetter
	 *
	 * pre none
	 * post the event has been acted upon if desiered
	 * 
	 * @param pe 		The pinsetter event that has been received.
	 */
	public void receivePinsetterEvent(PinsetterEvent pe) {
		
			if (pe.pinsDownOnThisThrow() >=  0) {			// this is a real throw
				markScore(currentGame.getCurrentThrower(), frameNumber + 1, pe.getThrowNumber(), pe.pinsDownOnThisThrow());
	
				// next logic handles the ?: what conditions dont allow them another throw?
				// handle the case of 10th frame first
				if (frameNumber == 9) {
					if (pe.totalPinsDown() == 10) {
						setter.resetPins();
						if(pe.getThrowNumber() == 1) {
							tenthFrameStrike = true;
						}
					}
				
					if ((pe.totalPinsDown() != 10) && (pe.getThrowNumber() == 2 && tenthFrameStrike == false)) {
						canThrowAgain = false;
						//publish( lanePublish() );
					}
				
					if (pe.getThrowNumber() == 3) {
						canThrowAgain = false;
						//publish( lanePublish() );
					}
				} else { // its not the 10th frame
			
					if (pe.pinsDownOnThisThrow() == 10) {		// threw a strike
						canThrowAgain = false;
						//publish( lanePublish() );
					} else if (pe.getThrowNumber() == 2) {
						canThrowAgain = false;
						//publish( lanePublish() );
					} else if (pe.getThrowNumber() == 3)  
						System.out.println("I'm here...");
				}
			} else {								//  this is not a real throw, probably a reset
			}
	}

	/** resetScores()
	 * 
	 * resets the scoring mechanism, must be called before scoring starts
	 * 
	 * pre the party has been assigned
	 * post scoring system is initialized
	 */
	public void resetScores() {
		Iterator bowlIt = (party.getMembers()).iterator();

		while ( bowlIt.hasNext() ) {
			int[] toPut = new int[25];
			for ( int i = 0; i != 25; i++){
				toPut[i] = -1;
			}
			scores.put( bowlIt.next(), toPut );
		}

		frameNumber = 0;
	}
		
	/** assignParty()
	 * 
	 * assigns a party to this lane
	 * 
	 * pre none
	 * post the party has been assigned to the lane
	 * 
	 * @param theParty		Simulation.Party to be assigned
	 */
	public void assignParty( Party theParty ) {
		party = theParty;
		
		curScores = new int[party.getMembers().size()];
		cumulScores = new int[party.getMembers().size()][10];
		finalScores = new int[party.getMembers().size()][128]; //Hardcoding a max of 128 games, bite me.
		gameNumber = 0;
		
		resetScores();

        this.interrupt(); // Party assigned, start the thread back up
	}

	/** markScore()
	 *
	 * Method that marks a bowlers score on the board.
	 * 
	 * @param Cur		The current bowler
	 * @param frame	The frame that bowler is on
	 * @param ball		The ball the bowler is on
	 * @param score	The bowler's score 
	 */
	private void markScore(Bowler Cur, int frame, int ball, int score ){
		int[] curScore;
		int index =  ( (frame - 1) * 2 + ball);

		curScore = (int[]) scores.get(Cur);

	
		curScore[ index - 1] = score;
		scores.put(Cur, curScore);
		newGetScore( Cur, frame );
		publish( lanePublish() );
	}

	/** lanePublish()
	 *
	 * Method that creates and returns a newly created laneEvent
	 * 
	 * @return		The new lane event
	 */
	public LaneEvent lanePublish() {
		LaneEvent laneEvent = new LaneEvent(
                party,
                bowlIndex,
                currentGame.getCurrentThrower(),
                cumulScores,
                scores,
                frameNumber+1,
                curScores,
                currentGame.getBall(),
                !(currentGame.getState() instanceof HaltedGameState)
        );
		return laneEvent;
	}


	/**
	 * This is the refactored getScore()
	 * It still exhibits terrible design.
	 * Because the cumulative scores are stored in a class variable, and associated with the given Bowler via a HashMap.
	 * This makes it hard to fully refactor the scores into BowlingFrames that are held within the Bowler - because
	 * that makes sense.
	 * This would allow the BowlingFrames to be persistent, allowing the StateMachine to be persistent for each Bowler.
	 * This is opposed to the current setup where the StateMachine needs to be created each time; and has to calculate
	 * all previous frames to get the current one.
	 *
	 * Also, this returns an int; it doesn't need to.
	 *
	 * @param bowler The Bowler we are getting the scores for
	 * @param frame This is useless TODO: remove this.
	 * @return an int representing the total (this is useless)
     */
	public int newGetScore(Bowler bowler, int frame){
        int[] myScores = (int[]) scores.get(bowler);
        ArrayList<BowlingFrame> frames = formatScoresToFrames(myScores);
        ScoreCalculatingStateContext context = new ScoreCalculatingStateContext(frames);
        int currTotal = context.calculateTotal();

        // work through each frame and set the score to the current total.
        int total = 0;
        for (int i = 0; i < frames.size(); i++){
            total += frames.get(i).getFrameScore();
            cumulScores[bowlIndex][i] = total;
        }
        return currTotal;
    }



    /**
     *
     * @param scores an array of scores that will be packaged into frames
     * @return An ArrayList of BowlingFrames
     */
    // This should be private, but it is public for the purpose of testing.
    public static ArrayList<BowlingFrame> formatScoresToFrames(int[] scores) {
        ArrayList<BowlingFrame> frames = new ArrayList<>();

        boolean shouldCreateFrame = true;
        BowlingFrame newFrame = new BowlingFrame(false);

        for (int i = 0; i < 21; i++){
            int score = scores[i];

            if (shouldCreateFrame){
                if (i < 18){
                    newFrame = new BowlingFrame(false);
                } else {
                    newFrame = new BowlingFrame(true); // you are on the last frame
                }
                try {
					System.out.println(score);
					newFrame.addRoll(score);
                } catch (BowlingFrame.FrameException e) {
                    e.printStackTrace();
                }
                shouldCreateFrame = false;
            } else {
                try {
					System.out.println(score);
					newFrame.addRoll(score);
                    // DO NOT ADD TO THE COLLECTION IF YOU ARE ON THE LAST SCORE (this prevents double-adding the score)
                    if ( i < 20) {
                        frames.add(newFrame); // you have added the frame
                    }
                } catch (BowlingFrame.FrameException e) {
                    e.printStackTrace();
                }

                if (i < 18) {
                    shouldCreateFrame = true;
                }
            }

        }
        return frames;
    }

    /** getScore()
	 *
	 * Method that calculates a bowlers score
	 * 
	 * @param Cur		The bowler that is currently up
	 * @param frame	The frame the current bowler is on
	 * 
	 * @return			The bowlers total score
	 */
	/*
	public int getScore(Bowler Cur, int frame) {
		int[] curScore;
		int strikeballs = 0;
		int totalScore = 0;
		curScore = (int[]) scores.get(Cur);
		for (int i = 0; i != 10; i++){
			cumulScores[bowlIndex][i] = 0;
		}
		int current = 2*(frame - 1)+ball-1;
		//Iterate through each ball until the current one.
		for (int i = 0; i != current+2; i++){
			//Spare:
			if( i%2 == 1 && curScore[i - 1] + curScore[i] == 10 && i < current - 1 && i < 19){
				//This ball was a the second of a spare.  
				//Also, we're not on the current ball.
				//Add the next ball to the ith one in cumul.
				cumulScores[bowlIndex][(i/2)] += curScore[i+1] + curScore[i]; 
				if (i > 1) {
					//cumulScores[bowlIndex][i/2] += cumulScores[bowlIndex][i/2 -1];
				}
			} else if( i < current && i%2 == 0 && curScore[i] == 10  && i < 18){
				strikeballs = 0;
				//This ball is the first ball, and was a strike.
				//If we can get 2 balls after it, good add them to cumul.
				if (curScore[i+2] != -1) {
					strikeballs = 1;
					if(curScore[i+3] != -1) {
						//Still got em.
						strikeballs = 2;
					} else if(curScore[i+4] != -1) {
						//Ok, got it.
						strikeballs = 2;
					}
				}
				if (strikeballs == 2){
					//Add up the strike.
					//Add the next two balls to the current cumulscore.
					cumulScores[bowlIndex][i/2] += 10;
					if(curScore[i+1] != -1) {
						cumulScores[bowlIndex][i/2] += curScore[i+1] + cumulScores[bowlIndex][(i/2)-1];
						if (curScore[i+2] != -1){
							if( curScore[i+2] != -2){
								cumulScores[bowlIndex][(i/2)] += curScore[i+2];
							}
						} else {
							if( curScore[i+3] != -2){
								cumulScores[bowlIndex][(i/2)] += curScore[i+3];
							}
						}
					} else {
						if ( i/2 > 0 ){
							cumulScores[bowlIndex][i/2] += curScore[i+2] + cumulScores[bowlIndex][(i/2)-1];
						} else {
							cumulScores[bowlIndex][i/2] += curScore[i+2];
						}
						if (curScore[i+3] != -1){
							if( curScore[i+3] != -2){
								cumulScores[bowlIndex][(i/2)] += curScore[i+3];
							}
						} else {
							cumulScores[bowlIndex][(i/2)] += curScore[i+4];
						}
					}
				} else {
					break;
				}
			}else { 
				//We're dealing with a normal throw, add it and be on our way.
				if( i%2 == 0 && i < 18){
					if ( i/2 == 0 ) {
						//First frame, first ball.  Set his cumul score to the first ball
						if(curScore[i] != -2){	
							cumulScores[bowlIndex][i/2] += curScore[i];
						}
					} else if (i/2 != 9){
						//add his last frame's cumul to this ball, make it this frame's cumul.
						if(curScore[i] != -2){
							cumulScores[bowlIndex][i/2] += cumulScores[bowlIndex][i/2 - 1] + curScore[i];
						} else {
							cumulScores[bowlIndex][i/2] += cumulScores[bowlIndex][i/2 - 1];
						}	
					}
				} else if (i < 18){ 
					if(curScore[i] != -1 && i > 2){
						if(curScore[i] != -2){
							cumulScores[bowlIndex][i/2] += curScore[i];
						}
					}
				}
				if (i/2 == 9){
					if (i == 18){
						cumulScores[bowlIndex][9] += cumulScores[bowlIndex][8];	
					}
					if(curScore[i] != -2){
						cumulScores[bowlIndex][9] += curScore[i];
					}
				} else if (i/2 == 10) {
					if(curScore[i] != -2){
						cumulScores[bowlIndex][9] += curScore[i];
					}
				}
			}
		}
		return totalScore;
	}*/

	/** isPartyAssigned()
	 *
	 * checks if a party is assigned to this lane
	 *
	 * @return true if party assigned, false otherwise
	 */
	public boolean isPartyAssigned() {
		return party != null;
	}

	/** subscribe
	 * 
	 * Method that will add a subscriber
	 *
	 */

	public void subscribe( LaneObserver adding ) {
		subscribers.add( adding );
	}

	/** unsubscribe
	 * 
	 * Method that unsubscribes an observer from this object
	 * 
	 * @param removing	The observer to be removed
	 */
	
	public void unsubscribe( LaneObserver removing ) {
		subscribers.remove( removing );
	}

	/** publish
	 *
	 * Method that publishes an event to subscribers
	 * 
	 * @param event	Event that is to be published
	 */

	public void publish( LaneEvent event ) {
		if( subscribers.size() > 0 ) {
			Iterator eventIterator = subscribers.iterator();
			
			while ( eventIterator.hasNext() ) {
				( (LaneObserver) eventIterator.next()).receiveLaneEvent( event );
			}
		}
	}

	/**
	 * Accessor to get this Lanes.Lanes's pinsetter
	 * 
	 * @return		A reference to this lane's pinsetter
	 */

	public Pinsetter getPinsetter() {
		return setter;	
	}

	/**
	 * Pause the execution of this game
	 */
	public void pauseGame() {
        currentGame.pause();
		publish(lanePublish());
	}
	
	/**
	 * Resume the execution of this game
	 */
	public void unPauseGame() {
        try {
            currentGame.unpause();
        } catch (Exception e) {
            e.printStackTrace();
        }
        publish(lanePublish());
	}

}
