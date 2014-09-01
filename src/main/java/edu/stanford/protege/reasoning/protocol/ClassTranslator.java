package edu.stanford.protege.reasoning.protocol;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassProvider;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 25/08/2014
 */
public class ClassTranslator implements Translator<OWLClass, Messages.ClassName> {

    private OWLClassProvider dataFactory;

    @Inject
    public ClassTranslator(OWLClassProvider dataFactory) {
        this.dataFactory = dataFactory;
    }

    @Override
    public OWLClass decode(Messages.ClassName message) {
        return dataFactory.getOWLClass(IRI.create(message.getIri()));
    }

    @Override
    public Messages.ClassName encode(OWLClass object) {
        return Messages.ClassName.newBuilder().setIri(object.getIRI().toString()).build();
    }

    @Override
    public OWLClass decode(byte[] bytes) throws IOException {
        return decode(Messages.ClassName.parseFrom(bytes));
    }
}
