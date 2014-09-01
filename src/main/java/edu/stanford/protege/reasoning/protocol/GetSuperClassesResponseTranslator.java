package edu.stanford.protege.reasoning.protocol;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import edu.stanford.protege.reasoning.KbQueryResult;
import edu.stanford.protege.reasoning.action.GetSuperClassesResponse;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLClassNodeSet;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class GetSuperClassesResponseTranslator implements Translator<GetSuperClassesResponse, Messages.GetSuperClassesResponseMessage> {

    private KbIdTranslator kbIdTranslator;

    private KbDigestTranslator kbDigestTranslator;

    private ClassExpressionTranslator classExpressionTranslator;

    private ClassNodeTranslator classNodeTranslator;

    private ConsistencyTranslator consistencyTranslator;

    @Inject
    public GetSuperClassesResponseTranslator(KbIdTranslator kbIdTranslator, KbDigestTranslator kbDigestTranslator, ClassExpressionTranslator classExpressionTranslator, ClassNodeTranslator classNodeTranslator, ConsistencyTranslator consistencyTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.kbDigestTranslator = kbDigestTranslator;
        this.classExpressionTranslator = classExpressionTranslator;
        this.classNodeTranslator = classNodeTranslator;
        this.consistencyTranslator = consistencyTranslator;
    }

    @Override
    public GetSuperClassesResponse decode(Messages.GetSuperClassesResponseMessage message) {
        Optional<KbQueryResult<NodeSet<OWLClass>>> result;
        if(message.getPresent()) {
            if(message.getConsistency() == Messages.Consistency.CONSISTENT) {
                Set<Node<OWLClass>> nodes = Sets.newHashSet();
                for(Messages.ClassHierarchyNode n : message.getSuperClassesList()) {
                    nodes.add(classNodeTranslator.decode(n));
                }
                NodeSet<OWLClass> nodeSet = new OWLClassNodeSet(nodes);
                result = KbQueryResult.optionalOfValue(nodeSet);
            }
            else {
                result = KbQueryResult.optionalOfInconsistentKb();
            }
        }
        else {
            result = Optional.absent();
        }
        return new GetSuperClassesResponse(
                kbIdTranslator.decode(message.getKbId()),
                kbDigestTranslator.decode(message.getKbDigest()),
                classExpressionTranslator.decode(message.getClassExpression()),
                result
        );
    }

    @Override
    public GetSuperClassesResponse decode(byte[] bytes) throws IOException {
        return decode(Messages.GetSuperClassesResponseMessage.parseFrom(bytes));
    }

    @Override
    public Messages.GetSuperClassesResponseMessage encode(GetSuperClassesResponse object) {
        Messages.GetSuperClassesResponseMessage.Builder builder = Messages.GetSuperClassesResponseMessage.newBuilder();
        builder.setKbId(kbIdTranslator.encode(object.getKbId()));
        builder.setKbDigest(kbDigestTranslator.encode(object.getKbDigest()));
        builder.setClassExpression(classExpressionTranslator.encode(object.getClassExpression()));
        Optional<KbQueryResult<NodeSet<OWLClass>>> result = object.getResult();
        if(result.isPresent()) {
            builder.setPresent(true);
            if (result.get().isConsistent()) {
                builder.setConsistency(Messages.Consistency.CONSISTENT);
                for(Node<OWLClass> n : result.get().getValue()) {
                    builder.addSuperClasses(classNodeTranslator.encode(n));
                }
            }
            else {
                builder.setConsistency(Messages.Consistency.INCONSISTENT);
            }
        }
        else {
            builder.setPresent(false);
        }
        return builder.build();
    }
}
