package edu.stanford.protege.reasoning.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.semanticweb.owlapi.change.AddAxiomData;
import org.semanticweb.owlapi.change.AxiomChangeData;
import org.semanticweb.owlapi.change.RemoveAxiomData;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLOntologyChangeVisitorAdapter;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 30/09/2014
 */
public class MinimizedLogicalAxiomChanges {

    private static final MinimizedLogicalAxiomChanges EMPTY = new MinimizedLogicalAxiomChanges(ImmutableList.<AxiomChangeData>of());

    private final ImmutableList<AxiomChangeData> minimizedLogicalAxiomChangeData;

    private MinimizedLogicalAxiomChanges(ImmutableList<AxiomChangeData> minimizedLogicalAxiomChangeData) {
        this.minimizedLogicalAxiomChangeData = checkNotNull(minimizedLogicalAxiomChangeData);
    }

    /**
     * Gets the logical axiom changes data in this {@code MinimizedLogicalAxiomChanges}.
     * @return The actual logical axioms change data.  Not {@code null}.  May be empty.
     */
    public List<AxiomChangeData> getLogicalAxiomChangeData() {
        return minimizedLogicalAxiomChangeData;
    }

    /**
     * Determines if this {@code MinimizedLogicalAxiomChanges} is empty.
     * @return {@code true} if this {@code MinimizedLogicalAxiomChanges} is empty, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return minimizedLogicalAxiomChangeData.isEmpty();
    }

    /**
     * Gets the empty {@code MinimizedLogicalAxiomChanges}.
     * @return The empty {@code MinimizedLogicalAxiomChanges}
     */
    public static MinimizedLogicalAxiomChanges empty() {
        return EMPTY;
    }

    /**
     * Obtains a set of minimised logical axiom changes from the specified ontology changes.
     * @param changes The changes.  Not {@code null}.
     * @return A {@code MinimizedLogicalAxiomChanges} representing the logical axiom changes that would be applied
     * from the specified ontology changes.  Not {@code null}.
     * @throws NullPointerException if {@code changes} is {@code null}.
     */
    public static MinimizedLogicalAxiomChanges fromOntologyChanges(ImmutableList<? extends OWLOntologyChange> changes) {
        checkNotNull(changes);
        if(changes.isEmpty()) {
            return EMPTY;
        }
        final Set<OWLLogicalAxiom> axiomsToAdd = Sets.newHashSet();
        final Set<OWLLogicalAxiom> axiomsToRemove = Sets.newHashSet();
        for(OWLOntologyChange change : changes) {
            change.accept(new OWLOntologyChangeVisitorAdapter() {
                @Override
                public void visit(RemoveAxiom change) {
                    if (change.getAxiom().isLogicalAxiom()) {
                        OWLLogicalAxiom logicalAxiom = (OWLLogicalAxiom) change.getAxiom();
                        if(!axiomsToAdd.remove(logicalAxiom)) {
                            axiomsToRemove.add(logicalAxiom);
                        }
                    }
                }

                @Override
                public void visit(AddAxiom change) {
                    if(change.getAxiom().isLogicalAxiom()) {
                        OWLLogicalAxiom logicalAxiom = (OWLLogicalAxiom) change.getAxiom();
                        if(!axiomsToRemove.remove(logicalAxiom)) {
                            axiomsToAdd.add(logicalAxiom);
                        }

                    }
                }
            });
        }
        final ImmutableList.Builder<AxiomChangeData> builder = ImmutableList.builder();
        for(OWLAxiom ax : axiomsToRemove) {
            builder.add(new RemoveAxiomData(ax));
        }
        for(OWLAxiom ax : axiomsToAdd) {
            builder.add(new AddAxiomData(ax));
        }
        return new MinimizedLogicalAxiomChanges(builder.build());
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof MinimizedLogicalAxiomChanges)) {
            return false;
        }
        MinimizedLogicalAxiomChanges other = (MinimizedLogicalAxiomChanges) o;
        return this.minimizedLogicalAxiomChangeData.equals(other.minimizedLogicalAxiomChangeData);
    }

    @Override
    public int hashCode() {
        return "MinimizedLogicalAxiomChanges".hashCode() + minimizedLogicalAxiomChangeData.hashCode();
    }


}
