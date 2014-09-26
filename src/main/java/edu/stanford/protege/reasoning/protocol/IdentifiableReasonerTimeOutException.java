package edu.stanford.protege.reasoning.protocol;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 26/09/2014
 */
public class IdentifiableReasonerTimeOutException {

    private int id;

    public IdentifiableReasonerTimeOutException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
