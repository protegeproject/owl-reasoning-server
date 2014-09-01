package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.KbId;
import org.semanticweb.owlapi.model.OWLClassExpression;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 16/04/2014
 */
public class GetSubClassesAction extends KbQueryAction<GetSubClassesResponse, GetSubClassesActionHandler> {

    public static final ActionType<GetSubClassesActionHandler> TYPE = ActionType.create();

    private final OWLClassExpression classExpression;

    @Inject
    public GetSubClassesAction(KbId kbId, OWLClassExpression classExpression) {
        super(kbId);
        this.classExpression = checkNotNull(classExpression);
    }

    public OWLClassExpression getClassExpression() {
        return classExpression;
    }

    @Override
    public ActionType<GetSubClassesActionHandler> getType() {
        return TYPE;
    }

    @Override
    public ListenableFuture<GetSubClassesResponse> dispatch(GetSubClassesActionHandler handler) {
        return handler.handleAction(this);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("GetSubClassesAction")
                .addValue(getKbId())
                .addValue(getClassExpression())
                .toString();
    }

    @Override
    public int hashCode() {
        return "GetSubClassesAction".hashCode()
                + getKbId().hashCode()
                + getClassExpression().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof GetSubClassesAction)) {
            return false;
        }
        GetSubClassesAction other = (GetSubClassesAction) o;
        return this.getKbId().equals(other.getKbId())
                && this.getClassExpression().equals(other.getClassExpression());
    }
}
