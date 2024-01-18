package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.models.layers.PheromoneLayer;

public class Evaporate<P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<Double, P> {
    public Evaporate(Environment<Double, P> environment, TimeDistribution<Double> distribution, Molecule molecule) {
        super(environment, distribution, molecule);
    }

    /**
     * get environment -> get layer -> get map -> foreach key -> update value or remove if value is <=0
     */
    @Override
    /*protected void action() {
        Environment<Double, P> environment = this.getEnvironment();
        Optional<Layer<Double, P>> optionalPheromoneLayer = environment.getLayer(this.getMolecule());

        if (optionalPheromoneLayer.isPresent()) {
            PheromoneLayer<P> layer = (PheromoneLayer<P>) optionalPheromoneLayer.get();
            var pheromoneMap = layer.getMap();

            pheromoneMap.entrySet().removeIf(entry -> {
                double updatedValue = entry.getValue() * 0.9;
                pheromoneMap.put(entry.getKey(), updatedValue);
                return updatedValue <= 0.0;
            });
        }
*/
    protected void action(PheromoneLayer<P> phLayer) {
        var pheromoneMap = phLayer.getMap();
        pheromoneMap.entrySet().removeIf(entry -> {
            double updatedValue = entry.getValue() * 0.9;
            pheromoneMap.put(entry.getKey(), updatedValue);
            return updatedValue <= 0.0;
        });
    }


}
