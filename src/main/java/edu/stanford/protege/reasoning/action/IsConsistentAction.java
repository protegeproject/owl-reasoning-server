package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.KbId;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class IsConsistentAction extends KbQueryAction<IsConsistentResponse, IsConsistentActionHandler> {

    public static final ActionType<IsConsistentActionHandler> TYPE = ActionType.create();

    public IsConsistentAction(KbId kbId) {
        super(kbId);
    }

    @Override
    public ActionType<IsConsistentActionHandler> getType() {
        return TYPE;
    }

    @Override
    public ListenableFuture<IsConsistentResponse> dispatch(IsConsistentActionHandler handler) {
        return handler.handleAction(this);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("IsConsistentAction").addValue(getKbId()).toString();
    }

    @Override
    public int hashCode() {
        return "IsConsistentAction".hashCode() + getKbId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof IsConsistentAction)) {
            return false;
        }
        IsConsistentAction other = (IsConsistentAction) o;
        return this.getKbId().equals(other.getKbId());
    }
}
