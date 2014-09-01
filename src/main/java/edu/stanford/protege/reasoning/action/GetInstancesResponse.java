package edu.stanford.protege.reasoning.action;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.KbQueryResult;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.NodeSet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 16/04/2014
 */
public class GetInstancesResponse extends KbQueryResultResponse<NodeSet<OWLNamedIndividual>> {

    private final OWLClassExpression classExpression;

    public GetInstancesResponse(KbId kbId, KbDigest kbDigest,
                                OWLClassExpression classExpression,
                                Optional<KbQueryResult<NodeSet<OWLNamedIndividual>>> result) {
        super(kbId, kbDigest, result);
        this.classExpression = checkNotNull(classExpression);
    }

    public OWLClassExpression getClassExpression() {
        return classExpression;
    }

    @Override
    public int hashCode() {
        return "GetInstancesResponse".hashCode() + getKbId().hashCode() + getKbDigest().hashCode()
                + getClassExpression().hashCode() + getResult().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof GetInstancesResponse)) {
            return false;
        }
        GetInstancesResponse other = (GetInstancesResponse) o;
        return this.getKbId().equals(other.getKbId())
                && this.getKbDigest().equals(other.getKbDigest())
                && this.getClassExpression().equals(other.getClassExpression())
                && this.getResult().equals(other.getResult());
    }
}
