package Simulation;

import javax.swing.*;

/**
 * Created by Nick on 4/29/2016.
 */
public interface CustomIterator {
    boolean hasNext();

    Object next();

    void process(JLabel[][] ballLabel);
}
