package edu.stanford.protege.reasoning.action;

import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/08/2014
 */
public abstract class KbQueryResponse implements Response {

    private final KbId kbId;

    private final KbDigest kbDigest;

    /**
     * Constructs a response.
     * @param kbId The knowledge base id of the knowledge base that the result pertains to.  Not {@code null}.
     * @param kbDigest The knowledge base digest of the knowledge base that the result pertains to.  Not {@code null}.
     * @throws NullPointerException if any parameters are {@code null}.
     */
    public KbQueryResponse(KbId kbId, KbDigest kbDigest) {
        this.kbId = checkNotNull(kbId);
        this.kbDigest = checkNotNull(kbDigest);
    }

    /**
     * Gets the KbId of the associated knowledge base.
     * @return The knowledge base Id of the knowledge base that the query was asked on.  Not {@code null}.
     */
    public KbId getKbId() {
        return kbId;
    }

    /**
     * Gets the digest of the associated knowledge base.
     * @return The knowledge base digest of the knowledge base that the query was asked on.  Not {@code null}.
     */
    public KbDigest getKbDigest() {
        return kbDigest;
    }

}
