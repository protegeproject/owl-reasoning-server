package edu.stanford.protege.reasoning;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
public class InternalReasonerErrorException extends RuntimeException {

    public InternalReasonerErrorException(String message) {
        super(message);
    }

    public InternalReasonerErrorException(Throwable cause) {
        super(cause);
    }
}
