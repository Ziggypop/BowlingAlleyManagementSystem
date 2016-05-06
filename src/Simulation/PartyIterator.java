package Simulation;

import Lanes.LaneEvent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nick on 4/29/2016.
 */
public class PartyIterator implements CustomIterator {

    private ArrayList<Bowler> bowlers;
    private int index = 0;

    public PartyIterator(Party party) {
        this.bowlers = new ArrayList<>();
        bowlers.addAll(party.getMembers());
    }

    @Override
    public boolean hasNext() {
        return this.index < this.bowlers.size();
    }

    @Override
    public Bowler next() {
        return bowlers.get(index++);
    }

    public void process(LaneEvent event, JLabel ballLabel[][], JLabel scoreLabel[][]) {
        int[][] scores = event.getCumulScore();
        Bowler currentBowler;
        int b = 0;
        while (this.hasNext()) {
            //process this bowlers frames and update ballLabel with score
            currentBowler = this.next();
            //System.out.println("Name of current bowler: " + currentBowler.getFullName());
            //System.out.println("Email of current bowler: " + currentBowler.getEmail());
            //for each frame/score for this bowler, update the ballLabel, increment f for each loop
            for (int f = 0; f <= event.getFrameNum() - 1; f++) { //for all frames for each bowler
                if (scores[b][f] != 0) { //if their score for the frame is not 0
                    scoreLabel[b][f].setText((new Integer(scores[b][f])).toString()); //set their score to the correct number
                }
            }
            for (int f = 0; f < 21; f++) { //look through bowlers' scores for each frame
                int curScore = ((int[]) (event.getScore()).get(bowlers.get(b)))[f];
                if (curScore != -1) {
                    if (curScore == 10 && (f % 2 == 0 || f == 19)) {
                        ballLabel[b][f].setText("X");
                    } else if (f > 0 && curScore + ((int[]) (event.getScore()).get(bowlers.get(b)))[f - 1] == 10 && f % 2 == 1)
                        ballLabel[b][f].setText("/");
                    else if (curScore == -2) {
                        System.out.println("Set to F");
                        ballLabel[b][f].setText("F");
                    } else {
                        ballLabel[b][f].setText((new Integer(((int[]) (event.getScore()).get(bowlers.get(b)))[f])).toString());
                    }
                }
            }
            b++;

        }
    }
}
