package edu.stanford.protege.reasoning.action;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.KbQueryResult;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/08/2014
 *
 * A response that wraps a knowledge base query result.
 */
public abstract class KbQueryResultResponse<R> extends KbQueryResponse {

    private final Optional<KbQueryResult<R>> result;

    /**
     * Constructs a response for the specified result.  The result is the result of a query on a consistent knowledge
     * base.
     * @param kbId The id of the knowledge base that the result pertains to.  Not {@code null}.
     * @param kbDigest The digest of the knowledge base that was queried.  Not {@code null}.
     * @param result The actual result.  This is specified as an optional in case the query could not be answered.
     *               Not {@code null}.
     * @throws NullPointerException if any parameters are {@code null}.
     */
    public KbQueryResultResponse(KbId kbId, KbDigest kbDigest, Optional<KbQueryResult<R>> result) {
        super(kbId, kbDigest);
        this.result = checkNotNull(result);
    }

    /**
     * Gets the query result as an optional.  An absent result indicates that the associated query could not be
     * answered.
     * @return The result.  Not {@code null}.
     */
    public final Optional<KbQueryResult<R>> getResult() {
        return result;
    }
}
