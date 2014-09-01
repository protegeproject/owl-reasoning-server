package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.KbQueryResult;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.reasoner.NodeSet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 16/04/2014
 */
public class GetSubClassesResponse extends KbQueryResultResponse<NodeSet<OWLClass>> {

    private final OWLClassExpression classExpression;

    public GetSubClassesResponse(KbId kbId, KbDigest kbDigest, OWLClassExpression classExpression, Optional<KbQueryResult<NodeSet<OWLClass>>> result) {
        super(kbId, kbDigest, result);
        this.classExpression = checkNotNull(classExpression);
    }

    public OWLClassExpression getClassExpression() {
        return classExpression;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("GetSubClassesResponse")
                .addValue(getKbId())
                .addValue(getKbDigest())
                .add("classExpression", classExpression)
                .addValue(getResult()).toString();
    }

    @Override
    public int hashCode() {
        return "GetSubClassesResponse".hashCode()
                + getKbId().hashCode()
                + getKbDigest().hashCode()
                + getClassExpression().hashCode()
                + getResult().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof GetSubClassesResponse)) {
            return false;
        }
        GetSubClassesResponse other = (GetSubClassesResponse) o;
        return this.getKbId().equals(other.getKbId())
                && this.getKbDigest().equals(other.getKbDigest())
                && this.getClassExpression().equals(other.getClassExpression())
                && this.getResult().equals(other.getResult());
    }
}
