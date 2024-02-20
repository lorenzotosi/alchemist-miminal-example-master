package it.unibo.alchemist.models.nodeProperty;

import it.unibo.alchemist.model.Node;
import it.unibo.alchemist.model.NodeProperty;
import it.unibo.alchemist.model.properties.AbstractNodeProperty;
import it.unibo.alchemist.models.myEnums.Directions;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Represents a node property that includes a direction.
 *
 * @param <T> the type of the node property value
 */
public class DirectionProperty<T> extends AbstractNodeProperty<T> {

    private Directions direction;

    /**
     * Constructs a new NodeWithDirection object with a random direction.
     *
     * @param node the node
     */
    public DirectionProperty(@NotNull Node<T> node) {
        super(node);
        this.direction = Directions.DEFAULT.getDirection(new Random().nextInt(8));
    }

    @NotNull
    @Override
    public NodeProperty<T> cloneOnNewNode(@NotNull Node<T> node) {
        return new DirectionProperty<>(node);
    }

    /**
     * Gets the direction of a node.
     *
     * @return the direction
     */
    public Directions getDirection(){
        return this.direction;
    }

    /**
     * Sets the direction of a node.
     *
     * @param direction the new direction
     * @param node the node 
     */
    public void setDirection(final Directions direction, final Node<T> node){
        this.direction = direction;
    }
}
