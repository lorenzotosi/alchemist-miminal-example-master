package it.unibo.alchemist.models.actions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.model.actions.AbstractAction;
import it.unibo.alchemist.model.molecules.SimpleMolecule;


public class CountNeighborhood extends AbstractAction<Integer> {
    private final Molecule molecule = new SimpleMolecule("neighborhood"); // molecule to be used to store the counter
    private final Node<Integer> node; // implicit value

    private final Environment<Integer, ?> environment; // implicit value

    public CountNeighborhood(Node<Integer> node, Environment<Integer, ?> environment) {
        super(node);
        this.node = node;
        this.environment = environment;
    }

    @Override
    public Action<Integer> cloneAction(Node<Integer> node, Reaction<Integer> reaction) {
        return new CountNeighborhood(node,  environment);
    }

    @Override
    public void execute() {
        var neighborhood = environment.getNeighborhood(node).getNeighbors().size();
        node.setConcentration(molecule, neighborhood);
    }

    @Override
    public Context getContext() {
        return Context.NEIGHBORHOOD; // it is local because it changes only the local molecule
    }
}
