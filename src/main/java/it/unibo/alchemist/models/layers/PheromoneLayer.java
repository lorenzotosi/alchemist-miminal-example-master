package it.unibo.alchemist.models.layers;

import it.unibo.alchemist.model.Environment;
import it.unibo.alchemist.model.Layer;
import it.unibo.alchemist.model.Molecule;
import it.unibo.alchemist.model.Position2D;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A layer used to assign a value to a possibly non-convex zone delimited by a sequence of points
 * @param <P> the Position type of the environment
 */
public class PheromoneLayer<P extends Position2D<P>> implements Layer<Double, P> {

    private final Environment<Double, P> environment;

    //p posizione, double valore
    private final Map<P, Double> map = new HashMap<>();

    private final Molecule molecule;

    public PheromoneLayer(Environment<Double, P> environment, Molecule molecule) {
        this.environment = environment;
        this.molecule = molecule;
    }

    @Override
    public Double getValue(P p) {
        Optional<Layer<Double, P>> layer = environment.getLayer(molecule);
        if (layer.isPresent()){
            return layer.get().getValue(p);
        } else  {
            return -1.5;
        }
    }

}