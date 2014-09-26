package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.ReasonerInternalErrorException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 03/09/2014
 */
public class IdentifiableReasonerInternalErrorException {

    private final int id;

    private final ReasonerInternalErrorException exception;

    public IdentifiableReasonerInternalErrorException(int id, ReasonerInternalErrorException exception) {
        this.id = checkNotNull(id);
        this.exception = checkNotNull(exception);
    }

    public int getId() {
        return id;
    }

    public ReasonerInternalErrorException getException() {
        return exception;
    }
}
