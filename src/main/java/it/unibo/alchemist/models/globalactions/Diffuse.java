package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.models.layers.PheromoneLayerImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Diffuse<T, P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<T, P> {
    private final Double customDiffusionThreshold;
    private final Double neighborhoodDistance;

    public Diffuse(final Environment<T, P> environment, final TimeDistribution<T> distribution, final Molecule molecule,
                   final Double customDiffusionThreshold, final Double neighborhoodDistance) {
        super(environment, distribution, molecule);
        this.customDiffusionThreshold = customDiffusionThreshold;
        this.neighborhoodDistance = neighborhoodDistance;
    }

    @Override
    protected void action(final PheromoneLayerImpl<P> phLayer) {
        var pheromoneMap = phLayer.getMap();
        var dummyMap = new HashMap<P, Double>();
        var nodeList = getEnvironment().getNodes().stream().map(a -> getEnvironment().getPosition(a)).toList();
        nodeList.forEach(p -> {
            var nPos = phLayer.adaptPosition(p);
            var pValue = pheromoneMap.getOrDefault(nPos, 0.0);
            if (pValue > customDiffusionThreshold)
                getNeighborhood(nPos).forEach(x -> dummyMap.put(x, pValue*0.5));
        });
        dummyMap.forEach(phLayer::deposit);

        /*pheromoneMap.forEach((key, value) -> {
                    if(value > customDiffusionThreshold){
                        getNeighborhood(key).forEach(x -> dummyMap.put(x, value*0.5));
                    }
                }
        );
        dummyMap.forEach(phLayer::addToMap);
        dummyMap.clear();*/

    }

    /**
     * ho una posizione, trovo il suo intorno e da queste nuove posizioni levo
     * quelle che eventualmente sono quelle di un nodo
     * @param position
     * @return
     */
    private List<P> getNeighborhood(final P position) {
        //var nodes = getEnvironment().getNodes().stream().map(a -> getEnvironment().getPosition(a)).toList();
        final var x = position.getX();
        final var y = position.getY();
        final double[] xs = DoubleStream.of(x - neighborhoodDistance, x, x + neighborhoodDistance).toArray();
        final double[] ys = DoubleStream.of(y - neighborhoodDistance, y, y + neighborhoodDistance).toArray();
        return Arrays.stream(xs).boxed()
                .flatMap(x1 -> Arrays.stream(ys).boxed().map(y1 -> getEnvironment().makePosition(x1, y1)))
                .filter(p -> !p.equals(position))
                //.filter(p -> !nodes.contains(p))
                .collect(Collectors.toList());
    }
}
