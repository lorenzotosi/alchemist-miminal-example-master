package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.models.layers.PheromoneLayer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Diffuse<T, P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<T, P> {
    private final Double customDiffusionThreshold;
    private final Double neighborhoodDistance;

    public Diffuse(Environment<T, P> environment, TimeDistribution<T> distribution, Molecule molecule,
                   Double customDiffusionThreshold, Double neighborhoodDistance) {
        super(environment, distribution, molecule);
        this.customDiffusionThreshold = customDiffusionThreshold;
        this.neighborhoodDistance = neighborhoodDistance;
    }

    @Override
    protected void action(PheromoneLayer<P> phLayer) {
        var pheromoneMap = phLayer.getMap();
        pheromoneMap.forEach((key, value) -> {
                    if(value>customDiffusionThreshold){
                        getNeighborhood(key).forEach(x -> pheromoneMap.put(x, value*0.5));
                    }
                }
        );
    }


    public List<P> getNeighborhood(P position) {
        final var x = position.getX();
        final var y = position.getY();
        final double[] xs = DoubleStream.of(x - neighborhoodDistance, x, x + neighborhoodDistance).toArray();
        final double[] ys = DoubleStream.of(y - neighborhoodDistance, y, y + neighborhoodDistance).toArray();
        return Arrays.stream(xs).boxed()
                .flatMap(x1 -> Arrays.stream(ys).boxed().map(y1 -> getEnvironment().makePosition(x1, y1)))
                .filter(p -> !p.equals(position))
                .collect(Collectors.toList());
    }
}
