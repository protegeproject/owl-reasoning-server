package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.KbId;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class IsEntailedAction extends KbQueryAction<IsEntailedResponse, IsEntailedActionHandler> {

    public static final ActionType<IsEntailedActionHandler> TYPE = ActionType.create();

    private final OWLLogicalAxiom axiom;

    public IsEntailedAction(KbId kbId, OWLLogicalAxiom axiom) {
        super(kbId);
        this.axiom = checkNotNull(axiom);
    }

    public OWLLogicalAxiom getAxiom() {
        return axiom;
    }

    @Override
    public ActionType<IsEntailedActionHandler> getType() {
        return TYPE;
    }

    @Override
    public ListenableFuture<IsEntailedResponse> dispatch(IsEntailedActionHandler handler) {
        return handler.handleAction(this);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("IsEntailedAction")
                .addValue(getKbId())
                .addValue(axiom)
                .toString();
    }

    @Override
    public int hashCode() {
        return "IsEntailedAction".hashCode() + getKbId().hashCode() + getAxiom().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof IsEntailedAction)) {
            return false;
        }
        IsEntailedAction other = (IsEntailedAction) o;
        return this.getKbId().equals(other.getKbId())
                && this.getAxiom().equals(other.getAxiom());
    }
}
