package it.unibo.alchemist.models.layers;

import it.unibo.alchemist.model.Environment;
import it.unibo.alchemist.model.Layer;
import it.unibo.alchemist.model.Position2D;

public class BlankLayer<Integer, P extends Position2D<P>> implements Layer<Integer, P> {

    private final Integer value;
    private final Environment<Integer, P> environment;

    public BlankLayer(Integer value, Environment<Integer, P> environment) {
        this.value = value;
        this.environment = environment;
    }


    @Override
    public Integer getValue(P p) {
        return null;
    }
}
