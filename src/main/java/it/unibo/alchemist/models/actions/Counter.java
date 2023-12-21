package it.unibo.alchemist.models.actions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.model.actions.AbstractAction;

/**
 * An action is an activity performed by a node.
 * Actions are executed by {@link Reaction}s.
 * Action are considered to be atomic, i.e. they are not interrupted.
 * Inside an action, you cannot perform unbound loops or wait for a condition to be satisfied.
 */
public class Counter extends AbstractAction<Double> {
    private final Molecule molecule;

    // Node is passed as implicit value!
    public Counter(Node<Double> node, Molecule molecule) {
        super(node);
        this.molecule = molecule;
    }
    @Override
    public Action<Double> cloneAction(Node<Double> node, Reaction<Double> reaction) {
        return new Counter(node, molecule);
    }

    @Override
    public void execute() {
        // Here you should write the code that will be executed by the node when it is triggered
        final double value = getNode().getConcentration(molecule);
        getNode().setConcentration(molecule, value + 1);
    }

    @Override
    public Context getContext() {
        return Context.LOCAL; // it is local because it changes only the local molecule
    }
}
