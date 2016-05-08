/**
 * CustomIterator.java
 */
package Simulation;

import Lanes.LaneEvent;

import javax.swing.*;

/**
 * Created by Nick on 4/29/2016
 */
public interface CustomIterator {
    /**
     * Determines if the iterator has more objects in its collection
     * @return true if there are more objects to process
     */
    boolean hasNext();

    /**
     * Returns the next object in the iterator collection to be processed
     * @return the next object in the collection
     */
    Object next();

    /**
     * Control is internal to iterator, updates gui labels given with information from bowlers in iterator
     * @param event the last LaneEvent that happened, gives info about bowlers and frame numbers
     * @param ballLabel the gui label that shows score for one frame
     * @param scoreLabel gui label that shows cumulative score
     */
    void process(LaneEvent event, JLabel[][] ballLabel, JLabel[][] scoreLabel);
}
