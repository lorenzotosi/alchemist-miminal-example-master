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
    protected void action(final PheromoneLayerImpl<P> phLayer) {
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
