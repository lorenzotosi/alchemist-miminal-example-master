package it.unibo.alchemist.models.actions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.model.actions.AbstractAction;
import it.unibo.alchemist.model.molecules.SimpleMolecule;
import it.unibo.alchemist.models.layer.PheromoneLayerImpl;
import it.unibo.alchemist.models.nodeProperty.DirectionProperty;

import java.util.Optional;


public class NodeInfo<T, P extends Position<P> & Position2D<P>> extends AbstractAction<T> {
    private final Molecule molecule = new SimpleMolecule("pheromone"); // molecule to be used to store the counter
    private final Node<T> node; // implicit value
    private final Environment<T, P> environment; // implicit value

    public NodeInfo(Node<T> node, Environment<T, P> environment) {
        super(node);
        this.node = node;
        this.environment = environment;
    }

    @Override
    public Action<T> cloneAction(Node<T> node, Reaction<T> reaction) {
        return new NodeInfo<>(node,  environment);
    }

    @Override
    public void execute() {
        Optional<Layer<T, P>> OptPhL = environment.getLayer(molecule);
        if (OptPhL.isPresent()){
            PheromoneLayerImpl<P> layer = (PheromoneLayerImpl<P>) OptPhL.get();
            var pos = environment.getPosition(node);
            var x = (DirectionProperty<T>) node.getProperties().get(1);
            node.setConcentration(new SimpleMolecule("PheromoneValue"), (T) layer.getValue(pos));
            node.setConcentration(new SimpleMolecule("Direction"), (T) x.getDirection());
        }

    }

    @Override
    public Context getContext() {
        return Context.NEIGHBORHOOD; // it is local because it changes only the local molecule
    }
}
