package it.unibo.alchemist.models.actions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.model.actions.AbstractAction;

public class Release<P extends Position<P>> extends AbstractAction<Integer> {

    private final Node<Integer> node; // implicit value

    private final Environment<Integer, P> environment; // implicit value
    /**
     * Call this constructor in the subclasses in order to automatically
     * instance the node.
     *
     * @param node the node this action belongs to
     */
    public Release(Node<Integer> node, Environment<Integer, P> environment) {
        super(node);
        this.node = node;
        this.environment = environment;
    }

    @Override
    public Action<Integer> cloneAction(Node<Integer> node, Reaction<Integer> reaction) {
        return new Release<>(node, environment);
    }

    @Override
    public void execute() {
        var currentPosition = environment.getPosition(node);
        var newPosition = currentPosition.plus(environment.makePosition(0, 1).getCoordinates());
        environment.moveNodeToPosition(node, newPosition);
    }


    @Override
    public Context getContext() {
        return Context.NEIGHBORHOOD;
    }
}
