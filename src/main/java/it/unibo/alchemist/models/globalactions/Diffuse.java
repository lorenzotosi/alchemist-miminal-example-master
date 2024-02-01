package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.models.layers.Patch;
import it.unibo.alchemist.models.layers.PheromoneLayer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Diffuse<T, P extends Position<P> & Position2D<P>> extends AbstractGlobalReaction<T, P> {
    private final Double customDiffusionThreshold;

    public Diffuse(final Environment<T, P> environment, final TimeDistribution<T> distribution, final Molecule molecule,
                   final Double customDiffusionThreshold, final Double neighborhoodDistance /*da eliminare*/) {
        super(environment, distribution, molecule);
        this.customDiffusionThreshold = customDiffusionThreshold;
    }

    @Override
    protected void action(final PheromoneLayer<P> phLayer) {
        var pheromoneMap = phLayer.getPatches();
        for (Patch[] patches : pheromoneMap) {
            for (Patch patch : patches) {
                double value = patch.getPheromoneConcentration();
                if (value > customDiffusionThreshold) {
                    var n = getNeighborhood(patch, pheromoneMap).stream().distinct().toList();
                    n.forEach(x -> x.increasePheromoneConcentration(value*0.5));
                }
            }
        }
    }

    /**
     * ho una dalla patch, trovo il suo intorno e lo ritorno come lista di patch.
     * il distinct serve per evitare duplicati
     * @param position
     * @return
     */
    private List<Patch> getNeighborhood(final Patch position, final Patch[][] patches) {
        final int x = position.getX();
        final int y = position.getY();
        final int[] xs = IntStream.of(x - 1, x, x + 1).map(i -> (i + patches.length) % patches.length).toArray();
        final int[] ys = IntStream.of(y - 1, y, y + 1).map(i -> (i + patches.length) % patches.length).toArray();
        return Arrays.stream(xs).boxed()
                .flatMap(x1 -> Arrays.stream(ys).boxed().map(y1 -> patches[x1][y1]))
                .filter(p -> !p.equals(position))
                .distinct()
                .collect(Collectors.toList());
    }
}
