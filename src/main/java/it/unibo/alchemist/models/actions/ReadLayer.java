package it.unibo.alchemist.models.actions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.model.actions.AbstractAction;
import it.unibo.alchemist.model.molecules.SimpleMolecule;
import it.unibo.alchemist.models.layers.PheromoneLayer;
import it.unibo.alchemist.models.nodeProperty.NodeWithDirection;

import java.util.Optional;


public class ReadLayer<P extends Position<P> & Position2D<P>> extends AbstractAction<Double> {
    private Molecule molecule = new SimpleMolecule("pheromone"); // molecule to be used to store the counter
    private final Node<Double> node; // implicit value
    private final Environment<Double, P> environment; // implicit value

    public ReadLayer(Node<Double> node, Environment<Double, P> environment) {
        super(node);
        this.node = node;
        this.environment = environment;
    }

    @Override
    public Action<Double> cloneAction(Node<Double> node, Reaction<Double> reaction) {
        return new ReadLayer<>(node,  environment);
    }

    @Override
    public void execute() {
        Optional<Layer<Double, P>> OptPhL = environment.getLayer(molecule);
        if (OptPhL.isPresent()){
            PheromoneLayer<P> layer = (PheromoneLayer<P>) OptPhL.get();
            var pos = environment.getPosition(node);
            //layer.addToMap(pos, node.getConcentration(molecule));
            //node.setConcentration(molecule, layer.getValue(pos));

            var x = (NodeWithDirection<Double>) node.getProperties().get(1);

            //System.out.println(x.getDirection());
            node.setConcentration(new SimpleMolecule("pheromoneLayer"), layer.getValue(pos));
/*
            var nodes = environment.getNodes().stream().map(environment::getPosition).toList();
            layer.getMap().forEach((x, y)->{
                if (!nodes.contains(x)){
                    var prototype = environment.getNodes().iterator().next();
                    var mynode = prototype.cloneNode(environment.getSimulation().getTime());
                    environment.addNode(mynode, x);
                }
            } );*/
        }

    }

    @Override
    public Context getContext() {
        return Context.NEIGHBORHOOD; // it is local because it changes only the local molecule
    }
}
