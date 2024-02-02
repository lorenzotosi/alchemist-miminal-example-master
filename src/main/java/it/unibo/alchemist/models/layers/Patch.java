package it.unibo.alchemist.models.layers;

import it.unibo.alchemist.model.Position2D;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class Patch  {

    //private double pheromoneConcentration;
    private final Double x;
    private final Double y;

    /*public Patch(final Double x, final Double y, double pheromoneConcentration) {
        this.pheromoneConcentration = pheromoneConcentration;
        this.x = x;
        this.y = y;
    }*/

    public Patch(final Double x, final Double y) {
        this.x = x;
        this.y = y;
    }

    /*public Double getPheromoneConcentration(){
        return this.pheromoneConcentration;
    }

    public void setPheromoneConcentration(final double value){
        this.pheromoneConcentration = value;
    }

    public void increasePheromoneConcentration(final double value){
        this.pheromoneConcentration += value;
    }*/

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patch patch)) return false;

        if (!Objects.equals(x, patch.x)) return false;
        return Objects.equals(y, patch.y);
    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }
}
