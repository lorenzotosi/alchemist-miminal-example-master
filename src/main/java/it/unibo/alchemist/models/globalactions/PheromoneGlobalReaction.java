package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.GlobalReaction;
import it.unibo.alchemist.model.Position;
import it.unibo.alchemist.model.Position2D;
import it.unibo.alchemist.models.layer.PheromoneLayerImpl;

/**
 * Represents a global reaction that acts on a PheromoneLayerImpl.
 *
 * @param <T> the type of data stored in the pheromone layer
 * @param <P> the type of position used in the pheromone layer
 */
public interface PheromoneGlobalReaction<T, P extends Position<P> & Position2D<P>> extends GlobalReaction<T> {
    /**
     * Performs an action on the given PheromoneLayerImpl.
     *
     * @param phLayer the pheromone layer on which the action is performed
     */
    void action(PheromoneLayerImpl<P> phLayer);
}
