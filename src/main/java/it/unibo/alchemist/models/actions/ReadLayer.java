package it.unibo.alchemist.models.actions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.model.actions.AbstractAction;
import it.unibo.alchemist.model.molecules.SimpleMolecule;


public class ReadLayer<P extends Position<P>> extends AbstractAction<Integer> {
    private final Molecule molecule = new SimpleMolecule("layerValue"); // molecule to be used to store the counter
    private final Molecule layer;
    private final Node<Integer> node; // implicit value

    private final Environment<Integer, P> environment; // implicit value

    public ReadLayer(Node<Integer> node, Environment<Integer, P> environment, Molecule layer) {
        super(node);
        this.node = node;
        this.environment = environment;
        this.layer = layer;
    }

    @Override
    public Action<Integer> cloneAction(Node<Integer> node, Reaction<Integer> reaction) {
        return new ReadLayer(node,  environment, layer);
    }

    @Override
    public void execute() {
        var layerValue = environment.getLayer(layer).get().getValue(environment.getPosition(node));
        node.setConcentration(molecule, layerValue);
    }

    @Override
    public Context getContext() {
        return Context.NEIGHBORHOOD; // it is local because it changes only the local molecule
    }
}
