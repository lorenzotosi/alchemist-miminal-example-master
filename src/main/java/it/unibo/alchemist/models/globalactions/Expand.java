package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.models.layers.PheromoneLayer;

public class Expand<T, P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<T, P>{
    public Expand(Environment<T, P> environment, TimeDistribution<T> distribution, Molecule molecule) {
        super(environment, distribution, molecule);
    }

    @Override
    protected void action(PheromoneLayer<P> phLayer) {

    }
}
