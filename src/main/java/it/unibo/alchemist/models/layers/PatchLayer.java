package it.unibo.alchemist.models.layers;

import it.unibo.alchemist.model.Layer;
import it.unibo.alchemist.model.Position2D;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class PatchLayer<P extends Position2D<P>> implements Layer<Double, P> {

    private final Patch[][] patches;

    public PatchLayer(final int width, final int height) {
        this.patches = new Patch[width][height];
        setupPatches(width, height);
        System.out.println("a");
    }

    private void setupPatches(final int width, final int height) {
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                this.patches[x][y] = new Patch(x, y, 0.0);
    }

    @Override
    public Double getValue(P p) {
        return patches[(int)p.getX()][(int)p.getY()].getPheromoneConcentration();
    }
}