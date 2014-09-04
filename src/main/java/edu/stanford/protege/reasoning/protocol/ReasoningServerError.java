package edu.stanford.protege.reasoning.protocol;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 03/09/2014
 */
public class ReasoningServerError {

    private final int id;

    private final String message;

    public ReasoningServerError(int id, String message) {
        this.id = checkNotNull(id);
        this.message = checkNotNull(message);
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
