package it.unibo.alchemist.models.layers;

import it.unibo.alchemist.model.Environment;
import it.unibo.alchemist.model.Layer;
import it.unibo.alchemist.model.Position;

public class IncreaseInTime<P extends Position<P>> implements Layer<Integer, P> {

    private final Integer value;

    private final Environment<Integer, P> environment;
    public IncreaseInTime(Integer value, Environment<Integer, P> environment) {
        this.value = value;
        this.environment = environment;
    }

    @Override
    public Integer getValue(P p) {
        return value + (int) environment.getSimulation().getTime().toDouble();
    }
}