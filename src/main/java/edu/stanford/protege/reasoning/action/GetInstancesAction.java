package edu.stanford.protege.reasoning.action;

import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.KbId;
import org.semanticweb.owlapi.model.OWLClassExpression;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 16/04/2014
 */
public class GetInstancesAction extends KbQueryAction<GetInstancesResponse, GetInstancesActionHandler> {

    public static final ActionType<GetInstancesActionHandler> TYPE = ActionType.create();

    private final OWLClassExpression classExpression;

    private final HierarchyQueryType hierarchyQueryType;

    /**
     * Constructs a {@link GetInstancesAction} for the specified knowledge base, class expression and hierarchy query
     * type.
     * @param kbId The knowledge base id.  Not {@code null}.
     * @param classExpression The class expression whose instances should be retrieved.  Not {@code null}.
     * @param hierarchyQueryType The hierarchy query type.  Not {@code null}.
     * @throws NullPointerException if any parameters are {@code null}.
     */
    public GetInstancesAction(KbId kbId, OWLClassExpression classExpression, HierarchyQueryType hierarchyQueryType) {
        super(kbId);
        this.classExpression = checkNotNull(classExpression);
        this.hierarchyQueryType = checkNotNull(hierarchyQueryType);
    }

    public OWLClassExpression getClassExpression() {
        return classExpression;
    }

    public HierarchyQueryType getHierarchyQueryType() {
        return hierarchyQueryType;
    }

    @Override
    public ActionType<GetInstancesActionHandler> getType() {
        return TYPE;
    }

    @Override
    public ListenableFuture<GetInstancesResponse> dispatch(GetInstancesActionHandler handler) {
        return handler.handleAction(this);
    }

    @Override
    public int hashCode() {
        return "GetInstancesAction".hashCode() + getKbId().hashCode() +  classExpression.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof GetInstancesAction)) {
            return false;
        }
        GetInstancesAction other = (GetInstancesAction) o;
        return this.getKbId().equals(other.getKbId()) && this.getClassExpression().equals(other.getClassExpression());
    }
}
