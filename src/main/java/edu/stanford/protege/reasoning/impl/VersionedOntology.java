package edu.stanford.protege.reasoning.impl;

import edu.stanford.protege.reasoning.KbDigest;
import org.semanticweb.owlapi.model.OWLOntology;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class VersionedOntology {

    private final OWLOntology ontology;

    private final KbDigest kbDigest;

    public VersionedOntology(OWLOntology ontology, KbDigest kbDigest) {
        this.ontology = checkNotNull(ontology);
        this.kbDigest = checkNotNull(kbDigest);
    }

    public KbDigest getKbDigest() {
        return kbDigest;
    }

    public OWLOntology getOntology() {
        return ontology;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof VersionedOntology)) {
            return false;
        }
        VersionedOntology other = (VersionedOntology) o;
        return this.kbDigest.equals(other.kbDigest);
    }
}
