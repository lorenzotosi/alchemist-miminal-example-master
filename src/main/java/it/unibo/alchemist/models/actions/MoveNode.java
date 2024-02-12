package it.unibo.alchemist.models.actions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.model.actions.AbstractAction;
import it.unibo.alchemist.models.layers.PheromoneLayerImpl;
import it.unibo.alchemist.models.myEnums.Directions;
import it.unibo.alchemist.models.nodeProperty.NodeWithDirection;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;


public class MoveNode<P extends Position<P> & Position2D<P>> extends AbstractAction<Double> {
    private final Node<Double> node;
    private final Environment<Double, P> environment;
    private final PheromoneLayerImpl<P> pheromoneLayer;
    private final Double sniffDistance;
    private final Double wiggleAngle;
    private final Double wiggleBias;
    private final Double sniffAngle;
    private final Double sniffThreshold;
    private final Molecule molecule;
    private final Map<P, Double> pheromoneMap;
    private final Rectangle layerBounds;



    public MoveNode(final Node<Double> node, final Environment<Double, P> environment, final double distance,
                    final Molecule molecule, final Double wiggleAngle, final Double wiggleBias,
                    final Double sniffAngle, final Double sniffThreshold) {
        super(node);
        this.node = node;
        this.environment = environment;
        this.sniffDistance = distance;
        this.molecule = molecule;
        this.wiggleAngle = wiggleAngle;
        this.wiggleBias = wiggleBias;
        this.sniffAngle = sniffAngle;
        this.sniffThreshold = sniffThreshold;
        this.pheromoneLayer = (PheromoneLayerImpl<P>) environment.getLayer(molecule).get();
        this.pheromoneMap = pheromoneLayer.getMap();
        this.layerBounds = pheromoneLayer.getLayerBounds();
    }

    @Override
    public Action<Double> cloneAction(final Node<Double> node, final Reaction<Double> reaction) {
        return new MoveNode<>(node, environment, sniffDistance, molecule, wiggleAngle, wiggleBias, sniffAngle, sniffThreshold);
    }

    @Override
    public void execute() {
        var currentPosition = environment.getPosition(node);
        var pos = pheromoneLayer.adaptPosition(currentPosition);
        var possibleDirections = getNeighborhood(pos).stream()
                .filter(x -> pheromoneMap.containsKey(x) && pheromoneMap.get(x)>sniffThreshold)
                .toList();

        Optional<P> maxPosition = possibleDirections.stream().filter(pheromoneMap::containsKey)
                .max(Comparator.comparingDouble(pheromoneMap::get));

        if (maxPosition.isPresent() && pheromoneMap.get(pos) > sniffThreshold) {
            updateNodeDirection(node, getDirectionFromCoordinates(pos.getX(), pos.getY(), maxPosition.get().getX(), maxPosition.get().getY()));
            var nextPosition = createNextPosition(maxPosition.get().getX(), maxPosition.get().getY(),
                    currentPosition, pos, layerBounds);
            environment.moveNodeToPosition(node, nextPosition);
        } else {
            var newDirection = Directions.DEFAULT.getDirection(new Random().nextInt(8));
            updateNodeDirection(node, newDirection);
            var newX = validateCoordinate((newDirection.getX() * sniffDistance) + currentPosition.getX(), layerBounds.getMinX(), layerBounds.getMaxX());
            var newY = validateCoordinate((newDirection.getY() * sniffDistance) + currentPosition.getY(), layerBounds.getMinY(), layerBounds.getMaxY());
            environment.moveNodeToPosition(node, environment.makePosition(newX, newY));
        }
    }

    private P findBestPosition(final List<P> neighborhoodPositions, final P nodePosition, final Directions direction){
        var directionValue = environment.makePosition((direction.getX()*sniffDistance)+nodePosition.getX(),
                (direction.getY()*sniffDistance) + nodePosition.getY());

        var possiblePositions = getPositionsInAngle(neighborhoodPositions, directionValue, nodePosition);
        Optional<P> maxPosition = possiblePositions.stream().filter(pheromoneMap::containsKey)
                .max(Comparator.comparingDouble(pheromoneMap::get));
        if (maxPosition.isPresent()){
            return environment.makePosition(maxPosition.get().getX(), maxPosition.get().getY());
        }
        var newdir = Directions.DEFAULT.getDirection(new Random().nextInt(8));
        updateNodeDirection(node, newdir);
        return environment.makePosition((newdir.getX()*sniffDistance)+nodePosition.getX(),
                (newdir.getY()*sniffDistance) + nodePosition.getY());
    }

    public Directions getDirectionFromCoordinates(double startX, double startY, double endX, double endY) {
        double diffX = endX - startX;
        double diffY = endY - startY;

        for (Directions dir : Directions.values()) {
            if (dir.getX() * sniffDistance == diffX && dir.getY() * sniffDistance == diffY) {
                return dir;
            }
        }
        return Directions.DEFAULT.getDirection(new Random().nextInt(8));
    }

    private P createNextPosition(final Double x, final Double y, final P currentPosition, final P pos, final Rectangle bounds) {
        var xx = x - pos.getX();
        var yy = y - pos.getY();

        return environment.makePosition(
                validateCoordinate(currentPosition.getX()+xx, bounds.getMinX(), bounds.getMaxX()),
                validateCoordinate(currentPosition.getY()+yy, bounds.getMinY(), bounds.getMaxY()));
    }

    private Double validateCoordinate(final Double coord, final Double minBound, final Double maxBound){
        if (coord <= maxBound && coord >= minBound){
            return coord;
        } else if (coord >= maxBound) {
            double distanceBeyondMax = coord - maxBound;
            double bouncedCoord = minBound + distanceBeyondMax;
            return Math.min(bouncedCoord, maxBound);
        } else {
            double distanceBeyondMin = minBound - coord;
            double bouncedCoord = maxBound - distanceBeyondMin;
            return Math.max(bouncedCoord, minBound);
        }
    }

    @Override
    public Context getContext() {
        return Context.NEIGHBORHOOD; // it is local because it changes only the local molecule
    }

    private List<P> getNeighborhood(final P position) {
        var nodes = environment.getNodes().stream().map(environment::getPosition).toList();
        var bounds = pheromoneLayer.getLayerBounds();
        final var x = position.getX();
        final var y = position.getY();
        final double[] xs = DoubleStream.of(x - sniffDistance, x, x + sniffDistance)
                .filter(value -> value >= bounds.getMinX() && value <= bounds.getMaxX())
                .toArray();
        final double[] ys = DoubleStream.of(y - sniffDistance, y, y + sniffDistance)
                .filter(value -> value >= bounds.getMinY() && value <= bounds.getMaxY())
                .toArray();
        return Arrays.stream(xs).boxed()
                .flatMap(x1 -> Arrays.stream(ys).boxed().map(y1 -> environment.makePosition(x1, y1)))
                .filter(p -> !p.equals(position))
                .filter(p -> !nodes.contains(p))
                .collect(Collectors.toList());
    }

    private Directions getCurrentNodeDirection(final Node<Double> node){
        var nodeProperty = (NodeWithDirection<Double>) node.getProperties().get(node.getProperties().size()-1);
        return nodeProperty.getDirection();
    }

    /**
     *
     * @param allPositions
     * @param forwardPosition davanti
     * @param center il mio nodo
     * @return
     */
    private List<P> getPositionsInAngle(List<P> allPositions, P forwardPosition, P center) {
        List<P> positionsInAngle = new ArrayList<>();
        for (P other : allPositions) {
            if (!forwardPosition.equals(other)) {
                double a = calculateAngle(center.getX(), center.getY(), forwardPosition.getX(), forwardPosition.getY(), other.getX(), other.getY());
                if (Math.abs(a) <= wiggleAngle || Math.abs(a) >= 360 - wiggleAngle) {
                    positionsInAngle.add(other);
                }
            }
        }
        return positionsInAngle;
    }

    private double calculateAngle(double centerX, double centerY, double x1, double y1, double x2, double y2) {
        double angle1 = Math.atan2(y1 - centerY, x1 - centerX);
        double angle2 = Math.atan2(y2 - centerY, x2 - centerX);

        double angle = angle2 - angle1;

        if (angle < -Math.PI) {
            angle += 2 * Math.PI;
        } else if (angle > Math.PI) {
            angle -= 2 * Math.PI;
        }

        return Math.toDegrees(angle);
    }

    private void updateNodeDirection(final Node<Double> node, final Directions directions){
        NodeWithDirection<Double> pNodeWithDirection = (NodeWithDirection<Double>) node.getProperties().get(1);
        pNodeWithDirection.setDirection(directions, node);
    }
}
