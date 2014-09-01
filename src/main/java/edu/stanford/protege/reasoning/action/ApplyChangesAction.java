package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.KbId;
import org.semanticweb.owlapi.change.AxiomChangeData;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class ApplyChangesAction extends KbUpdateAction<ApplyChangesResponse, ApplyChangesActionHandler> {

    public static final ActionType<ApplyChangesActionHandler> TYPE = ActionType.create();

    private final ImmutableList<AxiomChangeData> changeData;

    public ApplyChangesAction(KbId kbId, ImmutableList<AxiomChangeData> changeData) {
        super(kbId);
        this.changeData = checkNotNull(changeData);
    }

    @Override
    public ActionType<ApplyChangesActionHandler> getType() {
        return TYPE;
    }

    @Override
    public ListenableFuture<ApplyChangesResponse> dispatch(ApplyChangesActionHandler handler) {
        return handler.handleAction(this);
    }

    public ImmutableList<AxiomChangeData> getChangeData() {
        return changeData;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("ApplyChangesAction")
                      .addValue(getKbId())
                      .add("ChangeCount", changeData.size()).toString();
    }

    @Override
    public int hashCode() {
        return "ApplyChangesAction".hashCode() + getKbId().hashCode() + changeData.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof ApplyChangesAction)) {
            return false;
        }
        ApplyChangesAction other = (ApplyChangesAction) o;
        return this.getKbId().equals(other.getKbId())
                && this.changeData.equals(other.changeData);
    }
}
