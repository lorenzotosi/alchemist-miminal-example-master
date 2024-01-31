package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.models.layers.Patch;
import it.unibo.alchemist.models.layers.PheromoneLayer;

import java.util.Arrays;

public class Evaporate<P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<Double, P> {
    public Evaporate(final Environment<Double, P> environment, final TimeDistribution<Double> distribution,
                     final Molecule molecule) {
        super(environment, distribution, molecule);
    }

    @Override
    protected void action(final PheromoneLayer<P> phLayer) {
        var pheromoneMap = phLayer.getPatches();

        for (Patch[] patches : pheromoneMap) {
            for (Patch patch : patches) {
                double updatedValue = patch.getPheromoneConcentration() * 0.9;
                patch.setPheromoneConcentration(updatedValue);
            }
        }
    }


}
