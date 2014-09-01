package edu.stanford.protege.reasoning.action;

import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 16/04/2014
 */
public class KbUpdateResponse implements Response {

    private final KbId kbId;

    private final KbDigest kbDigest;

    public KbUpdateResponse(KbId kbId, KbDigest kbDigest) {
        this.kbId = checkNotNull(kbId);
        this.kbDigest = checkNotNull(kbDigest);
    }

    public KbId getKbId() {
        return kbId;
    }

    public KbDigest getKbDigest() {
        return kbDigest;
    }
}
