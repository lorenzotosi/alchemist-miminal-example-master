package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.models.layers.PheromoneLayerImpl;
import org.danilopianini.util.ListSet;

public class Deposit<T, P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<T, P> {
    private final ListSet<Node<T>> node;
    private final Double pheromoneDepositValue;

    public Deposit(final TimeDistribution<T> distribution, final Environment<T, P> environment,
                   final Molecule molecule, final Double pheromoneDepositValue) {
        super(environment, distribution, molecule);
        this.node = environment.getNodes();
        this.pheromoneDepositValue = pheromoneDepositValue;
    }

    @Override
    public void action(final PheromoneLayerImpl<P> phLayer) {
        final Environment<T, P> environment = this.getEnvironment();

        for (var node : this.node) {
            var pos = environment.getPosition(node);
            phLayer.deposit(pos, pheromoneDepositValue);
        }
    }

}
