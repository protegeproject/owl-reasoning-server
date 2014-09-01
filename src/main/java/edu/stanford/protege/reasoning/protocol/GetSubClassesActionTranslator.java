package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.GetSubClassesAction;
import org.semanticweb.owlapi.model.OWLClassExpression;

import javax.inject.Inject;
import java.io.IOException;

import static edu.stanford.protege.reasoning.protocol.Messages.ClassExpression;
import static edu.stanford.protege.reasoning.protocol.Messages.GetSubClassesActionMessage;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 21/08/2014
 */
public class GetSubClassesActionTranslator implements Translator<GetSubClassesAction, Messages.GetSubClassesActionMessage> {

    private final Translator<KbId, Messages.KbId> kbIdTranslator;

    private final Translator<OWLClassExpression, Messages.ClassExpression> classExpressionTranslator;

    @Inject
    public GetSubClassesActionTranslator(Translator<KbId, Messages.KbId> kbIdTranslator, Translator<OWLClassExpression, ClassExpression> classExpressionTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.classExpressionTranslator = classExpressionTranslator;
    }

    @Override
    public GetSubClassesAction decode(GetSubClassesActionMessage message) {
        return new GetSubClassesAction(
                kbIdTranslator.decode(message.getKbId()),
                classExpressionTranslator.decode(message.getClassExpression())
        );
    }

    @Override
    public GetSubClassesActionMessage encode(GetSubClassesAction object) {
        GetSubClassesActionMessage.Builder builder = GetSubClassesActionMessage.newBuilder();
        builder.setKbId(kbIdTranslator.encode(object.getKbId()));
        builder.setClassExpression(classExpressionTranslator.encode(object.getClassExpression()));
        return builder.build();
    }

    @Override
    public GetSubClassesAction decode(byte[] bytes) throws IOException {
        return decode(GetSubClassesActionMessage.parseFrom(bytes));
    }
}
