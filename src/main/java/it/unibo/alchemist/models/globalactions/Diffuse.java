package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.models.layer.PheromoneLayerImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * This class represents a global reaction that diffuses the pheromone value to its neighborhood.
 *
 * @param <T> The type of the molecule being diffused.
 * @param <P> The type of the position in the environment.
 */
public class Diffuse<T, P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<T, P> {
    private final Double customDiffusionThreshold;
    private final Double neighborhoodDistance;
    private final Double diffusionValue;

    /**
     * Constructs a new Diffuse object.
     *
     * @param environment             The environment in which the diffusion takes place.
     * @param distribution            The distribution used to determine the amount of molecule to diffuse.
     * @param molecule                The molecule to be diffused.
     * @param customDiffusionThreshold The custom diffusion threshold.
     * @param neighborhoodDistance    The distance within which the diffusion occurs.
     * @param diffusionValue          The value by which the molecule is diffused.
     */
    public Diffuse(final Environment<T, P> environment, final TimeDistribution<T> distribution, final Molecule molecule,
                   final Double customDiffusionThreshold, final Double neighborhoodDistance,
                   final Double diffusionValue) {
        super(environment, distribution, molecule);
        this.customDiffusionThreshold = customDiffusionThreshold;
        this.neighborhoodDistance = neighborhoodDistance;
        this.diffusionValue = diffusionValue;
    }

    /**
     * Performs the diffusion action on the given pheromone layer.
     *
     * @param phLayer The pheromone layer on which the diffusion action is performed.
     */
    @Override
    public void action(final PheromoneLayerImpl<P> phLayer) {
        var pheromoneMap = phLayer.getPheromoneMap();

        /*var nodeList = getEnvironment().getNodes().stream().map(a -> getEnvironment().getPosition(a)).toList();
        nodeList.forEach(p -> {
            var nPos = phLayer.adaptPosition(p);
            var pValue = pheromoneMap.getOrDefault(nPos, 0.0);
            if (pValue > customDiffusionThreshold)
                getNeighborhood(nPos).forEach(x -> phLayer.deposit(x, pValue * diffusionValue));
        });*/
        pheromoneMap.forEach((k, v) -> {
            if (v > customDiffusionThreshold){
                getNeighborhood(k).forEach(x -> phLayer.deposit(k, v * diffusionValue));
            }
        });
    }

    /**
     * Retrieves the neighborhood positions around the given position.
     *
     * @param position The position for which the neighborhood positions are retrieved.
     * @return A list of neighborhood positions.
     */
    private List<P> getNeighborhood(final P position) {
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
