package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 28/08/2014
 */
public class GetKbDigestResponse implements Response {

    private final KbId kbId;

    private final KbDigest kbDigest;

    public GetKbDigestResponse(KbId kbId, KbDigest kbDigest) {
        this.kbId = checkNotNull(kbId);
        this.kbDigest = checkNotNull(kbDigest);
    }

    public KbId getKbId() {
        return kbId;
    }

    public KbDigest getKbDigest() {
        return kbDigest;
    }

    @Override
    public int hashCode() {
        return "GetKbDigestResponse".hashCode() + kbId.hashCode() + kbDigest.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof GetKbDigestResponse)) {
            return false;
        }
        GetKbDigestResponse other = (GetKbDigestResponse) o;
        return this.kbId.equals(other.kbId) && this.kbDigest.equals(other.kbDigest);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("GetKbDigestResponse")
                .addValue(kbId)
                .addValue(kbDigest)
                .toString();
    }
}
