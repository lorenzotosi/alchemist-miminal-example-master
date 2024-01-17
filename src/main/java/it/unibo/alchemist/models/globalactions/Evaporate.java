package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.model.molecules.SimpleMolecule;
import it.unibo.alchemist.models.layers.PheromoneLayer;

import java.util.Map;
import java.util.Optional;

public class Evaporate <P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<Double, P> {
    public Evaporate(Environment<Double, P> environment, TimeDistribution<Double> distribution) {
        super(environment, distribution);
    }

    @Override
    protected void action() {
        Environment<Double, P> e = this.getEnvironment();
        Optional<Layer<Double, P>> OptPhL = e.getLayer(new SimpleMolecule("pheromone"));
        if (OptPhL.isPresent()){
            PheromoneLayer<P> layer = (PheromoneLayer<P>) OptPhL.get();
            var pheromoneMap = layer.getMap();
            if(!pheromoneMap.isEmpty()) {
                for (var entry : pheromoneMap.entrySet()) {
                    var key = entry.getKey();
                    Double value = entry.getValue() * 0.9;
                    pheromoneMap.put(key, value);
                }
            }
        }
    }

}
