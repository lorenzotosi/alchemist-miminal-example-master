package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.model.molecules.SimpleMolecule;
import it.unibo.alchemist.models.layers.PheromoneLayer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Evaporate <P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<Double, P> {
    public Evaporate(Environment<Double, P> environment, TimeDistribution<Double> distribution) {
        super(environment, distribution);
    }

    /**
     * get environment -> get layer -> get map -> foreach key -> update value or remove if value is <=0
     */
    @Override
    /*protected void action() {
        Environment<Double, P> e = this.getEnvironment();
        Optional<Layer<Double, P>> OptPhL = e.getLayer(new SimpleMolecule("pheromone"));
        if (OptPhL.isPresent()){
            PheromoneLayer<P> layer = (PheromoneLayer<P>) OptPhL.get();
            var pheromoneMap = layer.getMap();
            if(!pheromoneMap.isEmpty()) {
                for (var entry : pheromoneMap.entrySet()) {
                    var key = entry.getKey();
                    Double value = entry.getValue() * 0.9;
                    if (value <= 0.0){
                        pheromoneMap.remove(key, value);
                    } else {
                        pheromoneMap.put(key, value);
                    }
                }
            }
        }
    }*/
    protected void action() {
        Environment<Double, P> environment = this.getEnvironment();
        Optional<Layer<Double, P>> optionalPheromoneLayer = environment.getLayer(new SimpleMolecule("pheromone"));

        if (optionalPheromoneLayer.isPresent()) {
            PheromoneLayer<P> layer = (PheromoneLayer<P>) optionalPheromoneLayer.get();
            var pheromoneMap = layer.getMap();

            pheromoneMap.entrySet().removeIf(entry -> {
                double updatedValue = entry.getValue() * 0.9;
                pheromoneMap.put(entry.getKey(), updatedValue);
                return updatedValue <= 0.0;
            });
        }
    }


}
