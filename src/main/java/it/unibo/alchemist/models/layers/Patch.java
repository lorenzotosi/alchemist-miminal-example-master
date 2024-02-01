package it.unibo.alchemist.models.layers;

import it.unibo.alchemist.model.Position2D;

import java.awt.*;
import java.util.Arrays;

public class Patch  {

    private double pheromoneConcentration;
    private final int x;
    private final int y;

    public Patch(int x, int y, double pheromoneConcentration) {
        this.pheromoneConcentration = pheromoneConcentration;
        this.x = x;
        this.y = y;
    }

    public Double getPheromoneConcentration(){
        return this.pheromoneConcentration;
    }

    public void setPheromoneConcentration(final double value){
        this.pheromoneConcentration = value;
    }

    public void increasePheromoneConcentration(final double value){
        this.pheromoneConcentration += value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patch patch)) return false;

        if (x != patch.x) return false;
        return y == patch.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
