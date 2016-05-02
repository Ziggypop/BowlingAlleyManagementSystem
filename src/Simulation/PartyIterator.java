package Simulation;

import javax.swing.*;
import java.util.ArrayList;

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

    public void process(JLabel ballLabel[][]){
        Bowler currentBowler;
        while (this.hasNext()) {
            //process this bowlers frames and update ballLabel with score
            currentBowler = this.next();
        }
    }
}
