package it.unibo.alchemist.models.layers;

import it.unibo.alchemist.model.Layer;
import it.unibo.alchemist.model.Position2D;

public interface PheromoneLayer<P extends Position2D<P>> extends Layer<Double, P> {
    /**
     * Adds to the map in the p position the value
     *
     * @param p The patch position
     * @param value The pheromone value associated to the grid position
     */
    void deposit(P p, Double value);
}
