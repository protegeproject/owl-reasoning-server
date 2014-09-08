package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.KbId;
import org.semanticweb.owlapi.model.OWLClassExpression;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 08/09/2014
 */
public class GetEquivalentClassesAction extends KbQueryAction<GetEquivalentClassesResponse, GetEquivalentClassesActionHandler> {

    public static final ActionType<GetEquivalentClassesActionHandler> TYPE = ActionType.create();

    private final OWLClassExpression classExpression;

    public GetEquivalentClassesAction(KbId kbId, OWLClassExpression classExpression) {
        super(kbId);
        this.classExpression = checkNotNull(classExpression);
    }

    public OWLClassExpression getClassExpression() {
        return classExpression;
    }

    @Override
    public ActionType<GetEquivalentClassesActionHandler> getType() {
        return TYPE;
    }

    @Override
    public ListenableFuture<GetEquivalentClassesResponse> dispatch(GetEquivalentClassesActionHandler handler) {
        return handler.handleAction(this);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("GetEquivalentClassesAction")
                      .addValue(getKbId())
                      .add("classExpression", classExpression)
                      .toString();
    }

    @Override
    public int hashCode() {
        return "GetEquivalentClassesAction".hashCode() + getKbId().hashCode() + classExpression.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof GetEquivalentClassesAction)) {
            return false;
        }
        GetEquivalentClassesAction other = (GetEquivalentClassesAction) o;
        return this.getKbId().equals(other.getKbId()) && this.getClassExpression().equals(other.getClassExpression());
    }
}
