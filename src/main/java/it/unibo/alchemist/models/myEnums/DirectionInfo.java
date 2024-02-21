package it.unibo.alchemist.models.myEnums;

/**
 * This interface represents information about a direction.
 */
public interface DirectionInfo {
    /**
     * Returns the X value of the direction.
     *
     * @return the X value of the direction
     */
    Double getX();

    /**
     * Returns the Y value of the direction.
     *
     * @return the Y value of the direction
     */
    Double getY();

    /**
     * Returns the next left direction.
     *
     * @return the next left direction
     */
    Directions getMyLeft();

    /**
     * Returns the next right direction.
     *
     * @return the next right direction
     */
    Directions getMyRight();
}
