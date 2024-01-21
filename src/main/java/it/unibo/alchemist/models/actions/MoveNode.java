package it.unibo.alchemist.models.actions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.model.actions.AbstractAction;


public class MoveNode<P extends Position<P>> extends AbstractAction<Integer> {
    private final Node<Integer> node; // implicit value

    private final Environment<Integer, P> environment; // implicit value

    private final double distance;

    public MoveNode(Node<Integer> node, Environment<Integer, P> environment, double distance) {
        super(node);
        this.node = node;
        this.environment = environment;
        this.distance = distance;
    }

    @Override
    public Action<Integer> cloneAction(Node<Integer> node, Reaction<Integer> reaction) {
        return new MoveNode<>(node,  environment, distance);
    }

    @Override
    public void execute() {
       var currentPosition = environment.getPosition(node);
       var newPosition = currentPosition.plus(environment.makePosition(0, -1).getCoordinates());
       environment.moveNodeToPosition(node, newPosition);
    }

    @Override
    public Context getContext() {
        return Context.NEIGHBORHOOD; // it is local because it changes only the local molecule
    }
}
