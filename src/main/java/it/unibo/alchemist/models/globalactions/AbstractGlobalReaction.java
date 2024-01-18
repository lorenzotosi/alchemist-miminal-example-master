package it.unibo.alchemist.models.globalactions;

import it.unibo.alchemist.model.*;
import it.unibo.alchemist.models.layers.PheromoneLayer;
import org.danilopianini.util.ListSet;
import org.danilopianini.util.ListSets;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGlobalReaction<T, P extends Position<P> & Position2D<P>> implements GlobalReaction<T> {
    private final Environment<T, P> environment;
    private final TimeDistribution<T> distribution;
    private final List<Action<T>> actions = new ArrayList<>();
    private final List<Condition<T>> conditions = new ArrayList<>();
    private final Molecule molecule;

    public AbstractGlobalReaction(final Environment<T, P> environment, final TimeDistribution<T> distribution,
                                  final Molecule molecule) {
        this.environment = environment;
        this.distribution = distribution;
        this.molecule = molecule;
    }

    @NotNull
    @Override
    public List<Action<T>> getActions() {
        return actions;
    }

    @Override
    public void setActions(@NotNull List<? extends Action<T>> list) {
        actions.clear();
        actions.addAll(list);
    }

    @NotNull
    @Override
    public List<Condition<T>> getConditions() {
        return ListSets.emptyListSet();
    }

    @Override
    public void setConditions(@NotNull List<? extends Condition<T>> list) {
        conditions.clear();
        conditions.addAll(list);
    }

    @NotNull
    @Override
    public ListSet<? extends Dependency> getInboundDependencies() {
        return ListSets.emptyListSet();
    }

    @NotNull
    @Override
    public ListSet<? extends Dependency> getOutboundDependencies() {
        return ListSets.emptyListSet();
    }

    @NotNull
    @Override
    public TimeDistribution<T> getTimeDistribution() {
        return distribution;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public void execute() {
        environment.getLayer(molecule).ifPresent(phLayer -> action((PheromoneLayer<P>) phLayer));
        distribution.update(getTimeDistribution().getNextOccurence(), true, getRate(), environment);
    }

    @Override
    public void initializationComplete(@NotNull Time time, @NotNull Environment<T, ?> environment) {

    }

    @Override
    public void update(@NotNull Time time, boolean b, @NotNull Environment<T, ?> environment) {

    }

    @Override
    public int compareTo(@NotNull Actionable<T> o) {
        return getTau().compareTo(o.getTau());
    }

    protected abstract void action(final PheromoneLayer<P> phLayer);

    public Environment<T, P> getEnvironment() {
        return environment;
    }

    public Molecule getMolecule() {
        return molecule;
    }
}
