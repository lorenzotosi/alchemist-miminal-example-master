package it.unibo.alchemist.models.actions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.model.actions.AbstractAction;
import it.unibo.alchemist.models.layers.Patch;
import it.unibo.alchemist.models.layers.PheromoneLayer;
import it.unibo.alchemist.models.myEnums.Directions;
import it.unibo.alchemist.models.nodeProperty.NodeWithDirection;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;


public class MoveNode<P extends Position<P> & Position2D<P>> extends AbstractAction<Double> {
    private final Node<Double> node; // implicit value

    private final Environment<Double, P> environment; // implicit value
    private final PheromoneLayer<P> pheromoneLayer;
    private final Double sniffDistance;
    private final Double wiggleAngle;
    private final Double wiggleBias;
    private final Double sniffAngle;
    private final Double sniffThreshold;
    private final Molecule molecule;

    private final Patch[][] patches;



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
        this.pheromoneLayer = (PheromoneLayer<P>) environment.getLayer(molecule).get();
        this.patches = pheromoneLayer.getPatches();
    }

    @Override
    public Context getContext() {
        return Context.NEIGHBORHOOD; // it is local because it changes only the local molecule
    }

    @Override
    public Action<Double> cloneAction(final Node<Double> node, final Reaction<Double> reaction) {
        return new MoveNode<>(node, environment, sniffDistance, molecule, wiggleAngle, wiggleBias, sniffAngle, sniffThreshold);
    }

    @Override
    public void execute() {
        var realDoublePosition = environment.getPosition(node);
        var currentPosition = adaptCurrentNodePosition(realDoublePosition);



        /*
        var possiblePositions = getNeighborhood(currentPosition).stream()
                .filter(x -> patches.containsKey(x) && patches.get(x)>sniffThreshold)
                .toList();


        Optional<P> maxPosition = possiblePositions.stream().filter(patches::containsKey)
                .max(Comparator.comparingDouble(patches::get));
        maxPosition.ifPresent(p -> environment.moveNodeToPosition(node, p));
        */

    }

    private Patch adaptCurrentNodePosition(final P position){
        return new Patch((int)position.getX(), (int)position.getY(), 0.0);
    }

    private P findBestPosition(final List<P> neighborhoodPositions, final P nodePosition, final Directions direction){

        return null;
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
