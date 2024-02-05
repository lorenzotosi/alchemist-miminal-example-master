package it.unibo.alchemist.models.layers;

import it.unibo.alchemist.model.Position2D;

public interface PheromoneLayer<P extends Position2D<P>> {
    /**
     * Adds to the map in the p position the value
     *
     * @param p The patch position
     * @param value The pheromone value associated to the grid position
     */
    void addToMap(P p, Double value);

    /**
     * Adapts the value of a node position to it's nearest grid position
     *
     * @param position The node position
     * @return A grid position
     */
    P adaptPosition(final P position);
}
