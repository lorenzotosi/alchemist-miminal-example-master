package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.models.layers.PheromoneLayerImpl;
import org.danilopianini.util.ListSet;

/**
 * This class represents a global reaction that deposits a specific pheromone value on the PheromoneLayer.
 *
 * @param <T> the type of the entities in the environment
 * @param <P> the type of the positions in the environment
 */
public class Deposit<T, P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<T, P> {
    private final ListSet<Node<T>> node;
    private final Double pheromoneDepositValue;

    /**
     * Constructs a new Deposit object with the specified parameters.
     *
     * @param distribution          the time distribution for the reaction
     * @param environment           the environment in which the reaction occurs
     * @param molecule              the molecule associated with the reaction
     * @param pheromoneDepositValue the value of the pheromone to be deposited
     */
    public Deposit(final TimeDistribution<T> distribution, final Environment<T, P> environment,
                   final Molecule molecule, final Double pheromoneDepositValue) {
        super(environment, distribution, molecule);
        this.node = environment.getNodes();
        this.pheromoneDepositValue = pheromoneDepositValue;
    }

    /**
     * Performs the action of depositing the pheromone value on all nodes in the environment.
     *
     * @param phLayer the pheromone layer on which the deposit is performed
     */
    @Override
    public void action(final PheromoneLayerImpl<P> phLayer) {
        final Environment<T, P> environment = this.getEnvironment();

        for (var node : this.node) {
            var pos = environment.getPosition(node);
            phLayer.deposit(pos, pheromoneDepositValue);
        }
    }
}
