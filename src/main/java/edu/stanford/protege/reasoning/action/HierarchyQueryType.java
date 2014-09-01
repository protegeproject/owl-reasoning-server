package edu.stanford.protege.reasoning.action;

import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 27/08/2014
 */
public enum HierarchyQueryType {

    /**
     * Specifies that direct sub/super classes/properties/instances should be returned in the query.  See the
     * definitions on {@link OWLReasoner} in the OWL API for a definition of what direct means.
     */
    DIRECT(true),


    /**
     * Specifies that indirect sub/super classes/properties/instances should be returned in the query.  See the
     * definitions on {@link OWLReasoner} in the OWL API for a definition of what indirect means.
     */
    INDIRECT(false);

    private final boolean direct;

    HierarchyQueryType(boolean direct) {
        this.direct = direct;
    }

    /**
     * Determines if this type is equal to {@link #DIRECT}
     * @return {@code true} if this type is equal to {@link #DIRECT}, otherwise {@code false}.
     */
    public boolean isDirect() {
        return direct;
    }
}
