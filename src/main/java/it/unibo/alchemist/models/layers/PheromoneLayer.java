package it.unibo.alchemist.models.layers;

import it.unibo.alchemist.model.Environment;
import it.unibo.alchemist.model.Layer;
import it.unibo.alchemist.model.Molecule;
import it.unibo.alchemist.model.Position2D;
import kotlin.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PheromoneLayer<P extends Position2D<P>> implements Layer<Double, P> {

    private final Environment<Double, P> environment;
    private final Patch[][] patches;

    private final Molecule molecule;

    public PheromoneLayer(final Environment<Double, P> environment, final Molecule molecule,
                          final int width, final int height) {
        this.environment = environment;
        this.molecule = molecule;
        this.patches = new Patch[width][height];
        setupPatches(width, height);

    }

    private void setupPatches(final int width, final int height) {
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                this.patches[x][y] = new Patch(x, y, 0.0);
    }

    @Override
    public Double getValue(final P p) {
        return patches[(int)p.getX()][(int)p.getY()].getPheromoneConcentration();
    }

    public void addPheromone(final P p, final double concentration){
        patches[(int)p.getX()][(int)p.getY()].increasePheromoneConcentration(concentration);
    }
    public Patch[][] getPatches() {
        return patches;
    }

}