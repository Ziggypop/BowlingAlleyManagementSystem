/**
 * PartyIterator.java
 */
package Simulation;

import Lanes.LaneEvent;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Nick on 4/29/2016
 */
public class PartyIterator implements CustomIterator {

    //ArrayList of bowlers being processed
    private ArrayList<Bowler> bowlers;
    //index of bowler being processed
    private int index = 0;

    /**
     * PartyIterator constructor, creates the ArrayList of bowlers that will be processed
     * @param party the party of bowlers
     */
    public PartyIterator(Party party) {
        this.bowlers = new ArrayList<>();
        bowlers.addAll(party.getMembers());
    }

    /**
     * Determines if the iterator has more objects in its collection
     * @return true if there are more objects to process
     */
    @Override
    public boolean hasNext() {
        return this.index < this.bowlers.size();
    }

    /**
     * Returns the next object in the iterator collection to be processed
     * @return the next object in the collection
     */
    @Override
    public Bowler next() {
        return bowlers.get(index++);
    }

    /**
     * Control is internal to iterator, updates gui labels given with information from bowlers in iterator
     * @param event the last LaneEvent that happened, gives info about bowlers and frame numbers
     * @param ballLabel the gui label that shows score for one frame
     * @param scoreLabel gui label that shows cumulative score
     */
    public void process(LaneEvent event, JLabel ballLabel[][], JLabel scoreLabel[][]) {
        int[][] scores = event.getCumulScore();
        Bowler currentBowler;
        int b = 0;
        //process each bowler's frames and update ballLabel with score, b is number of current bowler
        while (this.hasNext()) {
            currentBowler = this.next();
            //for all frames that have happened, calculate cumulative score and update gui label, f is frame number
            for (int f = 0; f <= event.getFrameNum() - 1; f++) {
                if (scores[b][f] != 0) { //if their score for the frame is not 0
                    scoreLabel[b][f].setText((new Integer(scores[b][f])).toString());
                }
            }
            //for all frames, 1-20, get the score for that frame and update gui label
            for (int f = 0; f < 21; f++) {
                int curScore = ((int[]) (event.getScore()).get(bowlers.get(b)))[f];
                if (curScore != -1) {
                    if (curScore == 10 && (f % 2 == 0 || f == 19)) {
                        //if strike and frame is first of turn or 19th frame
                        ballLabel[b][f].setText("X");
                    } else if (f > 0 && curScore + ((int[]) (event.getScore()).get(bowlers.get(b)))[f - 1] == 10 && f % 2 == 1) {
                        //if two frames score add up to 10, meaning spare
                        ballLabel[b][f].setText("/");
                    }
                    else if (curScore == -2) {
                        //should not reach
                        System.out.println("Set to F");
                        ballLabel[b][f].setText("F");
                    } else {
                        //update gui label with score, no special state
                        ballLabel[b][f].setText((new Integer(curScore)).toString());
                    }
                }
            }
            b++;

        }
    }
}
