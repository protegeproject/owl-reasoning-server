package edu.stanford.protege.reasoning.protocol;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.KbQueryResult;
import edu.stanford.protege.reasoning.action.Consistency;
import edu.stanford.protege.reasoning.action.GetEquivalentClassesResponse;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 08/09/2014
 */
public class GetEquivalentClassesResponseTranslator implements Translator<GetEquivalentClassesResponse, Messages.GetEquivalentClassesResponseMessage> {


    private Translator<KbId, Messages.KbId> kbIdTranslator;

    private Translator<KbDigest, Messages.KbDigest> kbDigestTranslator;

    private Translator<Consistency, Messages.Consistency> consistencyTranslator;

    private Translator<OWLClassExpression, Messages.ClassExpression> classExpressionTranslator;

    private Translator<Node<OWLClass>, Messages.ClassHierarchyNode> classNodeTranslator;

    @Inject
    public GetEquivalentClassesResponseTranslator(
            Translator<KbId, Messages.KbId> kbIdTranslator,
            Translator<KbDigest, Messages.KbDigest> kbDigestTranslator,
            Translator<Consistency, Messages.Consistency> consistencyTranslator,
            Translator<OWLClassExpression, Messages.ClassExpression> classExpressionTranslator,
            Translator<Node<OWLClass>, Messages.ClassHierarchyNode> classNodeTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.kbDigestTranslator = kbDigestTranslator;
        this.consistencyTranslator = consistencyTranslator;
        this.classExpressionTranslator = classExpressionTranslator;
        this.classNodeTranslator = classNodeTranslator;
    }

    @Override
    public GetEquivalentClassesResponse decode(Messages.GetEquivalentClassesResponseMessage message) {
        Optional<KbQueryResult<Node<OWLClass>>> result;
        if(message.getPresent()) {
            if(message.getConsistency() == Messages.Consistency.CONSISTENT) {
                Node<OWLClass> node = classNodeTranslator.decode(message.getEquivalentClasses());
                result = KbQueryResult.optionalOfValue(node);
            }
            else {
                result = KbQueryResult.optionalOfInconsistentKb();
            }
        }
        else {
            result = Optional.absent();
        }

        return new GetEquivalentClassesResponse(
                kbIdTranslator.decode(message.getKbId()),
                kbDigestTranslator.decode(message.getKbDigest()),
                classExpressionTranslator.decode(message.getClassExpression()),
                result);
    }

    @Override
    public Messages.GetEquivalentClassesResponseMessage encode(GetEquivalentClassesResponse object) {
        Messages.GetEquivalentClassesResponseMessage.Builder builder = Messages.GetEquivalentClassesResponseMessage.newBuilder();
        builder.setKbId(kbIdTranslator.encode(object.getKbId()));
        builder.setKbDigest(kbDigestTranslator.encode(object.getKbDigest()));
        builder.setClassExpression(classExpressionTranslator.encode(object.getClassExpression()));
        builder.setPresent(object.getResult().isPresent());
        if(object.getResult().isPresent()) {
            KbQueryResult<Node<OWLClass>> result = object.getResult().get();
            builder.setConsistency(consistencyTranslator.encode(result.getConsistency()));
            if(result.isConsistent()) {
                builder.setEquivalentClasses(classNodeTranslator.encode(result.getValue()));
            }
        }

        return builder.build();
    }

    @Override
    public GetEquivalentClassesResponse decode(byte[] bytes) throws IOException {
        return decode(Messages.GetEquivalentClassesResponseMessage.parseFrom(bytes));
    }
}
