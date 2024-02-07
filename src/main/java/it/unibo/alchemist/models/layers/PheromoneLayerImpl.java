package it.unibo.alchemist.models.layers;

import it.unibo.alchemist.model.Environment;
import it.unibo.alchemist.model.Layer;
import it.unibo.alchemist.model.Molecule;
import it.unibo.alchemist.model.Position2D;

import java.util.HashMap;
import java.util.Map;

public class PheromoneLayerImpl<P extends Position2D<P>> implements PheromoneLayer<P> {

    private final Environment<Double, P> environment;

    //p posizione, double valore
    private final Map<P, Double> map;
    private final Double step;
    private final Double width;
    private final Double height;

    private final Molecule molecule;

    public PheromoneLayerImpl(final Environment<Double, P> environment, final Molecule molecule,
                              final Double startX, final Double startY,
                              final Double width, final Double height, final Double step) {
        this.environment = environment;
        this.molecule = molecule;
        this.map = new HashMap<>();
        this.step = step;
        this.width = width;
        this.height = height;

        for (double x = startX; x < width - Math.abs(startX); x = x + step){
            for (double y = startY; y < height - Math.abs(startY); y = y + step){
                map.put(environment.makePosition(x, y), 0.0);
            }
        }
        /*
        var x1 = roundToClosestPosition(-7.23, 0.25, 10);
        var x2 = roundToClosestPosition(7.23, 0.5, 10);
        var x3 = roundToClosestPosition(7.23, 1, 10);
        var x4 = roundToClosestPosition(5.73, 0.25, 10);
        var x5 = roundToClosestPosition(0.23, 0.25, 10);

        System.out.println("a");*/
    }
    @Override
    public void deposit(final P p, final Double value){
        var mapPosition = adaptPosition(p);
        if(map.containsKey(mapPosition))
            map.put(mapPosition, (value + map.get(mapPosition)));
        //else
            //map.put(mapPosition, value);
    }

    public Map<P, Double> getMap() {
        //return Map.copyOf(map);
        return this.map;
    }

    @Override
    public Double getValue(final P p) {
        return map.getOrDefault(adaptPosition(p), 0.0);
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

    public P adaptPosition(final P position){
        var x = roundToClosestPosition(position.getX(), step, width);
        var y = roundToClosestPosition(position.getY(), step, height);
        return environment.makePosition(x, y);
    }

}