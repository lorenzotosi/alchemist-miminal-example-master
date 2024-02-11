package it.unibo.alchemist.models.myEnums;

public interface DirectionInfo {
    /**
     *
     * @return the X value of the direction.
     */
    Double getX();

    /**
     *
     * @return the Y value of the direction.
     */
    Double getY();

    /**
     *
     * @return the next left Direction
     */
    Directions getMyLeft();

    /**
     *
     * @return the next right direction
     */
    Directions getMyRight();
}
