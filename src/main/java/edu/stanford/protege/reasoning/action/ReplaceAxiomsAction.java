package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.KbId;
import org.semanticweb.owlapi.model.OWLAxiom;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 28/08/2014
 */
public class ReplaceAxiomsAction extends KbAction<ReplaceAxiomsResponse, ReplaceAxiomsActionHandler> {

    public static final ActionType<ReplaceAxiomsActionHandler> TYPE = ActionType.create();

    private final ImmutableCollection<OWLAxiom> axioms;

    public ReplaceAxiomsAction(KbId kbId, ImmutableCollection<OWLAxiom> axioms) {
        super(kbId);
        this.axioms = checkNotNull(axioms);
    }

    public ImmutableCollection<OWLAxiom> getAxioms() {
        return axioms;
    }

    @Override
    public ActionType<ReplaceAxiomsActionHandler> getType() {
        return TYPE;
    }

    @Override
    public ListenableFuture<ReplaceAxiomsResponse> dispatch(ReplaceAxiomsActionHandler handler) {
        return handler.handleAction(this);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("ReplaceAxiomsAction")
                      .addValue(getKbId())
                      .add("size", axioms.size())
                      .toString();
    }

    @Override
    public int hashCode() {
        return "ReplaceAxiomsAction".hashCode() + getKbId().hashCode() + axioms.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof ReplaceAxiomsAction)) {
            return false;
        }
        ReplaceAxiomsAction other = (ReplaceAxiomsAction) o;
        return this.getKbId().equals(other.getKbId());
    }
}
