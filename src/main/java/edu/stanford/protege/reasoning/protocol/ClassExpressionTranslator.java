package edu.stanford.protege.reasoning.protocol;

import com.google.inject.Inject;
import org.semanticweb.owlapi.model.OWLClassExpression;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 21/08/2014
 */
public class ClassExpressionTranslator implements Translator<OWLClassExpression, Messages.ClassExpression> {

    private final BinaryOWLHelper binaryOWLHelper;

    @Inject
    public ClassExpressionTranslator(BinaryOWLHelper binaryOWLHelper) {
        this.binaryOWLHelper = binaryOWLHelper;
    }

    @Override
    public OWLClassExpression decode(Messages.ClassExpression message) {
        return binaryOWLHelper.decode(message.getSerialization());
    }

    @Override
    public Messages.ClassExpression encode(OWLClassExpression object) {
        return Messages.ClassExpression.newBuilder().setSerialization(binaryOWLHelper.encode(object)).build();
    }

    @Override
    public OWLClassExpression decode(byte[] bytes) throws IOException {
        return decode(Messages.ClassExpression.parseFrom(bytes));
    }
}
