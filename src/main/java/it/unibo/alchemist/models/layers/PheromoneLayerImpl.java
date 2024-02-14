package it.unibo.alchemist.models.layers;

import it.unibo.alchemist.model.Environment;
import it.unibo.alchemist.model.Molecule;
import it.unibo.alchemist.model.Position2D;
import it.unibo.alchemist.models.actions.MoveNode;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a layer of pheromones in the environment.
 * Pheromone values are stored in a map, where each position is associated with a pheromone value.
 * The map's punctilio positions represent the starting point of an area in which the nodes can deposit a pheromone.
 * Implementation of the {@link PheromoneLayer} interface.
 *
 * @param <P> the type of position in the environment
 */
public class PheromoneLayerImpl<P extends Position2D<P>> implements PheromoneLayer<P> {

    private final Environment<Double, P> environment;
    private final Map<P, Double> pheromoneMap;
    private final Double step;
    private final int width;
    private final int height;
    private final int startX;
    private final int startY;

    private final Molecule molecule;

    /**
     * PheromoneLayerImpl's constructor.
     *
     * @param environment the environment in which the pheromone layer exists
     * @param molecule the molecule associated with the pheromone layer
     * @param startX the starting x-coordinate of the layer
     * @param startY the starting y-coordinate of the layer
     * @param width the width of the layer
     * @param height the height of the layer
     * @param step the step size of the area
     */
    public PheromoneLayerImpl(final Environment<Double, P> environment, final Molecule molecule,
                              final int startX, final int startY,
                              final int width, final int height, final Double step) {
        this.environment = environment;
        this.molecule = molecule;
        this.pheromoneMap = new HashMap<>();
        this.step = step;
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;

        setupEnvironment(startX, startY);
    }
    
    @Override
    public void deposit(final P p, final Double value){
        var mapPosition = adaptPosition(p);
        if(pheromoneMap.containsKey(mapPosition))
            pheromoneMap.put(mapPosition, (value + pheromoneMap.get(mapPosition)));
        //else
            //map.put(mapPosition, value);
    }

    @Override
    public void evaporate(final P p, final Double value){
        if(pheromoneMap.containsKey(p))
            pheromoneMap.put(p, value>= 0 ? value : 0.0);
    }

    /**
     * Returns the pheromone map.
     *
     * @return a copy of the map
     */
    public Map<P, Double> getPheromoneMap() {
        return Map.copyOf(this.pheromoneMap);
    }

    /**
     * This method returns the pheromone map wrapped into an unmodifiable map.
     * @return the map
     */
    public Map<P, Double> getMap() {
        return Collections.unmodifiableMap(this.pheromoneMap);
    }

    @Override
    public Double getValue(final P p) {
        return pheromoneMap.getOrDefault(adaptPosition(p), 0.0);
    }

    /**
     * Adapts the position to the closest grid point in the PheromoneLayer.
     *
     * @param position the original position to be adapted
     * @return the adapted position
     */
    public P adaptPosition(final P position){
        var x = roundToClosestPosition(position.getX(), step, width);
        var y = roundToClosestPosition(position.getY(), step, height);
        return environment.makePosition(x, y);
    }

    @Override
    public Rectangle getLayerBounds(){
        return new Rectangle(startX, startY, width, height);
    }

    private double roundToClosestPosition(final double value, final double step, double maxValue) {
        int nSteps = (int) (value / step);
        double roundValue = nSteps * step;

        if (roundValue > maxValue) {
            return maxValue;
        }

        double nextRoundValue = (nSteps + 1) * step;
        if (Math.abs(value - roundValue) <= Math.abs(value - nextRoundValue)) {
            return roundValue;
        } else {
            return nextRoundValue;
        }
    }

    private void setupEnvironment(final int startX, final int startY){
        for (double x = startX; x <= width - Math.abs(startX); x = x + step){
            for (double y = startY; y <= height - Math.abs(startY); y = y + step){
                pheromoneMap.put(environment.makePosition(x, y), 0.0);
            }
        }
    }

}