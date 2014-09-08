package edu.stanford.protege.reasoning.protocol;

import com.google.inject.Inject;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.GetEquivalentClassesAction;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.reasoner.Node;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 08/09/2014
 */
public class GetEquivalentClassesActionTranslator implements Translator<GetEquivalentClassesAction,
        Messages.GetEquivalentClassesActionMessage> {

    private Translator<KbId, Messages.KbId> kbIdTranslator;

    private Translator<OWLClassExpression, Messages.ClassExpression> classExpressionTranslator;

    @Inject
    public GetEquivalentClassesActionTranslator(
            Translator<KbId, Messages.KbId> kbIdTranslator,
            Translator<OWLClassExpression, Messages.ClassExpression> classExpressionTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.classExpressionTranslator = classExpressionTranslator;
    }

    @Override
    public GetEquivalentClassesAction decode(Messages.GetEquivalentClassesActionMessage message) {
        return new GetEquivalentClassesAction(kbIdTranslator.decode(message.getKbId()),
                                              classExpressionTranslator.decode(message.getClassExpression()));
    }

    @Override
    public GetEquivalentClassesAction decode(byte[] bytes) throws IOException {
        return decode(Messages.GetEquivalentClassesActionMessage.parseFrom(bytes));
    }

    @Override
    public Messages.GetEquivalentClassesActionMessage encode(GetEquivalentClassesAction object) {
        Messages.GetEquivalentClassesActionMessage.Builder builder = Messages.GetEquivalentClassesActionMessage
                                                                             .newBuilder();
        return builder.setKbId(kbIdTranslator.encode(object.getKbId())).setClassExpression(
                classExpressionTranslator.encode(object.getClassExpression())).build();
    }
}
