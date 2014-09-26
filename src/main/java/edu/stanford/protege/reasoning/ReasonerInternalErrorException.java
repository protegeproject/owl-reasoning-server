package edu.stanford.protege.reasoning;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
public class ReasonerInternalErrorException extends RuntimeException {

    public ReasonerInternalErrorException(String message) {
        super(message);
    }

    public ReasonerInternalErrorException(Throwable cause) {
        super(cause);
    }
}
