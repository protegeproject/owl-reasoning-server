package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.GetSuperClassesAction;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class GetSuperClassesActionTranslator implements Translator<GetSuperClassesAction, Messages.GetSuperClassesActionMessage> {

    private KbIdTranslator kbIdTranslator;

    private ClassExpressionTranslator classExpressionTranslator;

    private HierachyQueryTypeTranslator hierachyQueryTypeTranslator;

    @Inject
    public GetSuperClassesActionTranslator(KbIdTranslator kbIdTranslator, ClassExpressionTranslator classExpressionTranslator, HierachyQueryTypeTranslator hierachyQueryTypeTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.classExpressionTranslator = classExpressionTranslator;
        this.hierachyQueryTypeTranslator = hierachyQueryTypeTranslator;
    }

    @Override
    public GetSuperClassesAction decode(Messages.GetSuperClassesActionMessage message) {
        return new GetSuperClassesAction(
                kbIdTranslator.decode(message.getKbId()),
                classExpressionTranslator.decode(message.getClassExpression()),
                hierachyQueryTypeTranslator.decode(message.getHierarchyQueryType())
        );
    }

    @Override
    public GetSuperClassesAction decode(byte[] bytes) throws IOException {
        return decode(Messages.GetSuperClassesActionMessage.parseFrom(bytes));
    }

    @Override
    public Messages.GetSuperClassesActionMessage encode(GetSuperClassesAction object) {
        return Messages.GetSuperClassesActionMessage.newBuilder()
                .setKbId(kbIdTranslator.encode(object.getKbId()))
                .setClassExpression(classExpressionTranslator.encode(object.getClassExpression()))
                .setHierarchyQueryType(hierachyQueryTypeTranslator.encode(object.getHierarchyQueryType()))
                .build();
    }
}
