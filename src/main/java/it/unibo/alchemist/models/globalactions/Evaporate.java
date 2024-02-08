package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.models.layers.PheromoneLayerImpl;


/**
 * This class represents a global reaction that evaporates pheromones in a pheromone layer.
 *
 * @param <T> the type of the entities in the environment
 * @param <P> the type of the positions in the environment
 */
public class Evaporate<T, P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<T, P> {
    private final Double evaporationValue;

    /**
     * Constructs a new Evaporate object with the specified environment, time distribution,
     * molecule, and evaporation value.
     *
     * @param environment      the environment in which the reaction occurs
     * @param distribution     the time distribution for the reaction
     * @param molecule         the molecule associated with the reaction
     * @param evaporationValue the value by which the pheromones will be evaporated
     */
    public Evaporate(final Environment<T, P> environment, final TimeDistribution<T> distribution,
                     final Molecule molecule, final Double evaporationValue) {
        super(environment, distribution, molecule);
        this.evaporationValue = evaporationValue;
    }

    /**
     * Performs the action of evaporating pheromones in the pheromone layer.
     *
     * @param phLayer the pheromone layer on which the action is performed
     */
    @Override
    public void action(final PheromoneLayerImpl<P> phLayer) {
        var pheromoneMap = phLayer.getMap();
        pheromoneMap.forEach((key, value) -> {
            if (value > 0) {
                pheromoneMap.put(key, value * evaporationValue);
            }
        });
    }
}
