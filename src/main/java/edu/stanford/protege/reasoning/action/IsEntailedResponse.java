package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.*;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class IsEntailedResponse extends KbQueryResultResponse<Entailed> {

    private final OWLLogicalAxiom axiom;

    public IsEntailedResponse(KbId kbId, KbDigest kbDigest, OWLLogicalAxiom axiom, Optional<KbQueryResult<Entailed>> result) {
        super(kbId, kbDigest, result);
        this.axiom = checkNotNull(axiom);
    }

    public OWLLogicalAxiom getAxiom() {
        return axiom;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("IsEntailedResponse")
                .addValue(getKbId())
                .addValue(getKbDigest())
                .addValue(getAxiom())
                .addValue(getResult())
                .toString();
    }

    @Override
    public int hashCode() {
        return "IsEntailedResponse".hashCode()
                + getKbId().hashCode()
                + getKbDigest().hashCode()
                + getAxiom().hashCode()
                + getResult().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof IsEntailedResponse)) {
            return false;
        }
        IsEntailedResponse other = (IsEntailedResponse) o;
        return this.getKbId().equals(other.getKbId())
                && this.getKbDigest().equals(other.getKbDigest())
                && this.getAxiom().equals(other.getAxiom())
                && this.getResult().equals(other.getResult());
    }
}
