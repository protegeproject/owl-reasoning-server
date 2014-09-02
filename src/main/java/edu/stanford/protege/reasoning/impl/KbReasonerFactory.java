package edu.stanford.protege.reasoning.impl;

import edu.stanford.protege.reasoning.KbId;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
public interface KbReasonerFactory {

    KbReasoner createReasoner(KbId kbId);
}
