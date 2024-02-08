package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.GlobalReaction;
import it.unibo.alchemist.model.Position;
import it.unibo.alchemist.model.Position2D;
import it.unibo.alchemist.models.layers.PheromoneLayerImpl;

public interface PheromoneGlobalReaction<T, P extends Position<P> & Position2D<P>> extends GlobalReaction<T> {
    void action(PheromoneLayerImpl<P> phLayer);

}
