package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.models.layers.PheromoneLayer;
import org.danilopianini.util.ListSet;

public class Deposit<P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<Double, P> {
    private final ListSet<Node<Double>> node;
    private final Double pheromoneDepositValue;

    public Deposit(final TimeDistribution<Double> distribution, final Environment<Double, P> environment,
                   final Molecule molecule, final Double pheromoneDepositValue) {
        super(environment, distribution, molecule);
        this.node = environment.getNodes();
        this.pheromoneDepositValue = pheromoneDepositValue;
    }

    @Override
    protected void action(final PheromoneLayer<P> phLayer) {
        final Environment<Double, P> environment = this.getEnvironment();

        for (var node : this.node) {
            var pos = environment.getPosition(node);
            phLayer.addPheromone(pos, pheromoneDepositValue);
        }
    }

}
