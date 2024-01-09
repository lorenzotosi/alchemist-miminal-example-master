package it.unibo.alchemist.models.actions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.model.actions.AbstractAction;
import it.unibo.alchemist.model.molecules.SimpleMolecule;
import it.unibo.alchemist.models.layers.PheromoneLayer;

import java.util.Optional;


public class ReadLayer<P extends Position<P> & Position2D<P>> extends AbstractAction<Double> {
    private Molecule molecule = new SimpleMolecule("pheromone"); // molecule to be used to store the counter
    private final Node<Double> node; // implicit value
    private final Environment<Double, P> environment; // implicit value
    private final int nodeCount;

    /*public ReadLayer(Node<Integer> node, Environment<Integer, P> environment, Molecule layer) {
        super(node);
        this.node = node;
        this.environment = environment;
        this.mValue = layer;

    }

        @Override
    public Action<Integer> cloneAction(Node<Integer> node, Reaction<Integer> reaction) {
        return new ReadLayer(node,  environment, mValue);
    }

    */

    public ReadLayer(Node<Double> node, Environment<Double, P> environment, int nodeCount) {
        super(node);
        this.node = node;
        this.environment = environment;
        this.nodeCount = nodeCount;
    }

    @Override
    public Action<Double> cloneAction(Node<Double> node, Reaction<Double> reaction) {
        return new ReadLayer<>(node,  environment, nodeCount);
    }

    @Override
    public void execute() {
        //var layerValue = environment.getLayer(mValue).get().getValue(environment.getPosition(node));
        //node.setConcentration(molecule, layerValue);


        Optional<Layer<Double, P>> OptPhL = environment.getLayer(molecule);
        if (OptPhL.isPresent()){
            PheromoneLayer<P> layer = (PheromoneLayer<P>) OptPhL.get();
            var pos = environment.getPosition(node);
            layer.addToMap(pos, node.getConcentration(molecule));
        }

        /*if (environment.getNodeCount() < nodeCount){
            var prototype = environment.getNodes().iterator().next();
            var xx = environment.getPosition(prototype);
            var node = prototype.cloneNode(environment.getSimulation().getTime());
            environment.addNode(node, environment
                    .getPosition(prototype)
                    .plus(environment.makePosition(0, -1).getCoordinates()));
        }*/

        /*var x = environment.getLayer(molecule).get().getValue(environment.getPosition(node));
        System.out.println(environment.getPosition(node).toString());
        System.out.println(x);*/

    }

    @Override
    public Context getContext() {
        return Context.NEIGHBORHOOD; // it is local because it changes only the local molecule
    }
}
