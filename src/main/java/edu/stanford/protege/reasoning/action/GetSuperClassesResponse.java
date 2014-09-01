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
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class GetSuperClassesResponse extends KbQueryResponse {

    private OWLClassExpression classExpression;

    private Optional<KbQueryResult<NodeSet<OWLClass>>> result;

    public GetSuperClassesResponse(KbId kbId, KbDigest kbDigest,
                                   OWLClassExpression classExpression,
                                   Optional<KbQueryResult<NodeSet<OWLClass>>> result) {
        super(kbId, kbDigest);
        this.classExpression = checkNotNull(classExpression);
        this.result = checkNotNull(result);
    }

    public OWLClassExpression getClassExpression() {
        return classExpression;
    }

    public Optional<KbQueryResult<NodeSet<OWLClass>>> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("GetSuperClassesResponse")
                .addValue(getKbId())
                .addValue(getKbDigest())
                .addValue(classExpression)
                .addValue(result)
                .toString();
    }

    @Override
    public int hashCode() {
        return "GetSuperClassesResponse".hashCode()
                + getKbId().hashCode()
                + getKbDigest().hashCode()
                + classExpression.hashCode()
                + result.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof GetSuperClassesResponse)) {
            return false;
        }
        GetSuperClassesResponse other = (GetSuperClassesResponse) o;
        return this.getKbId().equals(other.getKbId())
                && this.getKbDigest().equals(other.getKbDigest())
                && this.getClassExpression().equals(other.getClassExpression())
                && this.getResult().equals(other.getResult());
    }
}
