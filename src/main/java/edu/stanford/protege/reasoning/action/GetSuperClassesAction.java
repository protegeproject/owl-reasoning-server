package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.KbId;
import org.semanticweb.owlapi.model.OWLClassExpression;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class GetSuperClassesAction extends KbAction<GetSuperClassesResponse, GetSuperClassesActionHandler> {

    public static final ActionType<GetSuperClassesActionHandler> TYPE = ActionType.create();

    private final OWLClassExpression classExpression;

    private final HierarchyQueryType hierarchyQueryType;

    public GetSuperClassesAction(KbId kbId, OWLClassExpression classExpression, HierarchyQueryType hierarchyQueryType) {
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
    public ActionType<GetSuperClassesActionHandler> getType() {
        return TYPE;
    }

    @Override
    public ListenableFuture<GetSuperClassesResponse> dispatch(GetSuperClassesActionHandler handler) {
        return handler.handleAction(this);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("GetSuperClassesAction")
                .addValue(getKbId())
                .addValue(classExpression)
                .toString();
    }

    @Override
    public int hashCode() {
        return "GetSuperClassesResponse".hashCode()
                + getKbId().hashCode()
                + getClassExpression().hashCode()
                + getHierarchyQueryType().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof GetSuperClassesAction)) {
            return false;
        }
        GetSuperClassesAction other = (GetSuperClassesAction) o;
        return this.getKbId().equals(other.getKbId())
                && this.getClassExpression().equals(other.getClassExpression())
                && this.getHierarchyQueryType().equals(other.getHierarchyQueryType());
    }
}
