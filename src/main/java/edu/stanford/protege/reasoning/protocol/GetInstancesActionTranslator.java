package edu.stanford.protege.reasoning.protocol;

import com.google.inject.Inject;
import edu.stanford.protege.reasoning.action.HierarchyQueryType;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.GetInstancesAction;
import org.semanticweb.owlapi.model.OWLClassExpression;

import java.io.IOException;


/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 27/08/2014
 */
public class GetInstancesActionTranslator implements Translator<GetInstancesAction, Messages.GetInstancesActionMessage> {

    private Translator<KbId, Messages.KbId> kbIdTranslator;

    private Translator<OWLClassExpression, Messages.ClassExpression> classExpressionTranslator;

    private Translator<HierarchyQueryType, Messages.HierarchyQueryType> hierarchyQueryTypeTranslator;

    @Inject
    public GetInstancesActionTranslator(Translator<KbId, Messages.KbId> kbIdTranslator, Translator<OWLClassExpression, Messages.ClassExpression> classExpressionTranslator, Translator<HierarchyQueryType, Messages.HierarchyQueryType> hierarchyQueryTypeTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.classExpressionTranslator = classExpressionTranslator;
        this.hierarchyQueryTypeTranslator = hierarchyQueryTypeTranslator;
    }

    @Override
    public GetInstancesAction decode(Messages.GetInstancesActionMessage message) {
        return new GetInstancesAction(
                kbIdTranslator.decode(message.getKbId()),
                classExpressionTranslator.decode(message.getClassExpresion()),
                hierarchyQueryTypeTranslator.decode(message.getHierarchyQueryType())
        );
    }

    @Override
    public Messages.GetInstancesActionMessage encode(GetInstancesAction object) {
        Messages.GetInstancesActionMessage.Builder builder = Messages.GetInstancesActionMessage.newBuilder();
        builder.setKbId(kbIdTranslator.encode(object.getKbId()));
        builder.setClassExpresion(classExpressionTranslator.encode(object.getClassExpression()));
        builder.setHierarchyQueryType(hierarchyQueryTypeTranslator.encode(object.getHierarchyQueryType()));
        return builder.build();
    }

    @Override
    public GetInstancesAction decode(byte[] bytes) throws IOException {
        return decode(Messages.GetInstancesActionMessage.parseFrom(bytes));
    }
}
