package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.KbQueryResult;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.reasoner.Node;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 08/09/2014
 */
public class GetEquivalentClassesResponse extends KbQueryResultResponse<Node<OWLClass>> {

    private final OWLClassExpression classExpression;

    public GetEquivalentClassesResponse(
            KbId kbId,
            KbDigest kbDigest, OWLClassExpression classExpression, Optional<KbQueryResult<Node<OWLClass>>> result) {
        super(kbId, kbDigest, result);
        this.classExpression = checkNotNull(classExpression);
    }

    public OWLClassExpression getClassExpression() {
        return classExpression;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("GetEquivalentClassesResponse")
                      .addValue(getKbId())
                      .addValue(getKbDigest())
                      .add("classExpression", classExpression)
                      .addValue(getResult())
                      .toString();
    }

    @Override
    public int hashCode() {
        return "GetEquivalentClassesResponse".hashCode()
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
        if(!(o instanceof GetEquivalentClassesResponse)) {
            return false;
        }
        GetEquivalentClassesResponse other = (GetEquivalentClassesResponse) o;
        return this.getKbId().equals(other.getKbId())
                && this.getKbDigest().equals(other.getKbDigest())
                && this.getClassExpression().equals(other.getClassExpression())
                && this.getResult().equals(other.getResult());
    }
}
