package it.unibo.alchemist.models.layers;

import it.unibo.alchemist.model.Environment;
import it.unibo.alchemist.model.Layer;
import it.unibo.alchemist.model.Molecule;
import it.unibo.alchemist.model.Position2D;
import kotlin.Pair;

import java.util.*;

public class PheromoneLayer<P extends Position2D<P>> implements Layer<Double, P> {

    private final Environment<Double, P> environment;
    private final Map<Patch, Double> patches;

    private final Molecule molecule;

    public PheromoneLayer(final Environment<Double, P> environment, final Molecule molecule,
                          final int width, final int height) {
        this.environment = environment;
        this.molecule = molecule;
        this.patches = new LinkedHashMap<>();
        setupPatches(width, height);

    }

    private void setupPatches(final int width, final int height, final Double step,
                              final Double startX, final Double startY) {
        for(Double x = startX; x < width; x = x + step)
            for(Double y = startY; y < height; y = y + step)
                this.patches.put(new Patch(x, y), 0.0);
    }

    @Override
    public Double getValue(final P p) {
        return patches.getOrDefault(new Patch(p.getX(), p.getY()), 0.0);
    }

    public void addPheromone(final P p, final double concentration){

    }
    public Map<Patch, Double> getPatches() {
        return patches;
    }

}