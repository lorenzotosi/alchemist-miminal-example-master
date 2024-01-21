package it.unibo.alchemist.models.nodeProperty;

import it.unibo.alchemist.model.Node;
import it.unibo.alchemist.model.NodeProperty;
import it.unibo.alchemist.model.properties.AbstractNodeProperty;
import it.unibo.alchemist.models.myEnums.Directions;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class NodeWithDirection<T> extends AbstractNodeProperty<T> {

    private final String direction;

    public NodeWithDirection(@NotNull Node<T> node) {
        super(node);
        this.direction = String.valueOf(Directions.DEFAULT.getDirection(new Random().nextInt(4)));
    }

    @NotNull
    @Override
    public NodeProperty<T> cloneOnNewNode(@NotNull Node<T> node) {
        return new NodeWithDirection<>(node);
    }

    public String getDirection(){
        return this.direction;
    }
}
