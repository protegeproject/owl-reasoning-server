package edu.stanford.protege.reasoning.protocol;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 27/08/2014
 */
public class NamedIndividualTranslator implements Translator<OWLNamedIndividual, Messages.IndividualName> {

    private OWLDataFactory dataFactory;

    @Inject
    public NamedIndividualTranslator(OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }

    @Override
    public OWLNamedIndividual decode(Messages.IndividualName message) {
        return dataFactory.getOWLNamedIndividual(IRI.create(message.getIri()));
    }

    @Override
    public Messages.IndividualName encode(OWLNamedIndividual object) {
        return Messages.IndividualName.newBuilder()
                .setIri(object.getIRI().toString())
                .build();
    }

    @Override
    public OWLNamedIndividual decode(byte[] bytes) throws IOException {
        return decode(Messages.IndividualName.parseFrom(bytes));
    }
}
