/**
 * CustomCollection.java
 * Interface for collection objects that can generate a custom iterator
 */
package Simulation;

/**
 * Created by Nick on 5/1/2016
 */
public interface CustomCollection {

    /**
     * Returns the iterator for this class
     * @return the iterator generated for this class
     */
    public CustomIterator getIterator();

}
