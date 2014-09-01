package edu.stanford.protege.reasoning.protocol;

import org.semanticweb.owlapi.model.OWLLogicalAxiom;

import javax.inject.Inject;
import java.io.IOException;

import static edu.stanford.protege.reasoning.protocol.Messages.Axiom;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 20/08/2014
 */
public class AxiomTranslator implements Translator<OWLLogicalAxiom, Messages.Axiom> {

    private final BinaryOWLHelper binaryOWLHelper;

    @Inject
    public AxiomTranslator(BinaryOWLHelper binaryOWLHelper) {
        this.binaryOWLHelper = binaryOWLHelper;
    }

    @Override
    public OWLLogicalAxiom decode(Axiom message) {
        return binaryOWLHelper.decode(message.getSerialization());
    }

    @Override
    public Axiom encode(OWLLogicalAxiom object) {
        return Axiom.newBuilder().setSerialization(binaryOWLHelper.encode(object)).build();
    }

    @Override
    public OWLLogicalAxiom decode(byte[] bytes) throws IOException {
        return decode(Axiom.parseFrom(bytes));
    }
}
