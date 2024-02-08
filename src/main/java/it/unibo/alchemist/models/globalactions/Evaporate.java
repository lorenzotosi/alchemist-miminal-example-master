package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.models.layers.PheromoneLayerImpl;

public class Evaporate<T, P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<T, P> {
    private final Double evaporationValue;
    public Evaporate(final Environment<T, P> environment, final TimeDistribution<T> distribution,
                     final Molecule molecule, final Double evaporationValue) {
        super(environment, distribution, molecule);
        this.evaporationValue = evaporationValue;
    }

    @Override
    public void action(final PheromoneLayerImpl<P> phLayer) {
        var pheromoneMap = phLayer.getMap();
        /*pheromoneMap.entrySet().removeIf(entry -> {
            double updatedValue = entry.getValue() * 0.9;
            if (updatedValue > 0.0){
                pheromoneMap.put(entry.getKey(), updatedValue);
            }
            return updatedValue <= 0.0;
        });
         */
        pheromoneMap.forEach((key, value) -> {
            if(value > 0){
                pheromoneMap.put(key, value*evaporationValue);
            }
        });
    }


}
