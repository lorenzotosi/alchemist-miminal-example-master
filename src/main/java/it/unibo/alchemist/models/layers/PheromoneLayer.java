package it.unibo.alchemist.models.layers;

import it.unibo.alchemist.model.Environment;
import it.unibo.alchemist.model.Layer;
import it.unibo.alchemist.model.Molecule;
import it.unibo.alchemist.model.Position2D;
import kotlin.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PheromoneLayer<P extends Position2D<P>> implements Layer<Double, P> {

    private final Environment<Double, P> environment;

    //p posizione, double valore
    private final Map<P, Double> map;

    private final Molecule molecule;

    public PheromoneLayer(Environment<Double, P> environment, Molecule molecule) {
        this.environment = environment;
        this.molecule = molecule;
        this.map = new HashMap<>();
    }

    public void addToMap(P p, Double value){
        if(map.containsKey(p))
            map.put(p, (value + map.get(p)));
        else
            map.put(p, value);
    }

    public Map<P, Double> getMap() {
        return Map.copyOf(map);
    }

    @Override
    public Double getValue(P p) {
        /*Optional<Layer<Double, P>> layer = environment.getLayer(molecule);
        if (layer.isPresent()){
            return layer.get().getValue(p);
        } else  {
            return 0.0;
        }*/
        return map.getOrDefault(p, 0.0);
    }

}