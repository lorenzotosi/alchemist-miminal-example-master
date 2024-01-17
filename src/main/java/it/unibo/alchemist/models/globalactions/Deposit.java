package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.model.molecules.SimpleMolecule;
import it.unibo.alchemist.models.layers.PheromoneLayer;
import org.danilopianini.util.ListSet;

import java.util.Optional;

public class Deposit<P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<Double, P> {

    private Molecule molecule; // molecule to be used to store the counter
    private final ListSet<Node<Double>> node; // implicit value
    public Deposit( TimeDistribution<Double> distribution, Environment<Double, P> environment) {
        super(environment, distribution);
        this.molecule = new SimpleMolecule("pheromone");
        this.node = environment.getNodes();
    }

    @Override
    protected void action() {
        Environment<Double, P> e = this.getEnvironment();
        Optional<Layer<Double, P>> OptPhL = e.getLayer(molecule);

        for(var node : this.node){
            if (OptPhL.isPresent()){
                PheromoneLayer<P> layer = (PheromoneLayer<P>) OptPhL.get();
                var pos = e.getPosition(node);
                layer.addToMap(pos, node.getConcentration(molecule));
            }
        }

    }
}
