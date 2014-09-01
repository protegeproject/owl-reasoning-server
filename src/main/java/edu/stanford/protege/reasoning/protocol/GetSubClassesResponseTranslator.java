package edu.stanford.protege.reasoning.protocol;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import edu.stanford.protege.reasoning.action.Consistency;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.KbQueryResult;
import edu.stanford.protege.reasoning.action.GetSubClassesResponse;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLClassNodeSet;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

import static edu.stanford.protege.reasoning.protocol.Messages.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 21/08/2014
 */
public class GetSubClassesResponseTranslator implements Translator<GetSubClassesResponse, Messages.GetSubClassesResponseMessage> {

    private final Translator<KbId, Messages.KbId> kbIdTranslator;

    private final Translator<KbDigest, Messages.KbDigest> kbDigestTranslator;

    private final Translator<OWLClassExpression, ClassExpression> classExpressionTranslator;

    private final Translator<Node<OWLClass>, ClassHierarchyNode> classNodeTranslator;

    private final Translator<Consistency, Messages.Consistency> consistencyTranslator;

    @Inject
    public GetSubClassesResponseTranslator(Translator<KbId, Messages.KbId> kbIdTranslator, Translator<KbDigest, Messages.KbDigest> kbDigestTranslator, Translator<OWLClassExpression, ClassExpression> classExpressionTranslator, Translator<Node<OWLClass>, ClassHierarchyNode> classNodeTranslator, Translator<Consistency, Messages.Consistency> consistencyTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.kbDigestTranslator = kbDigestTranslator;
        this.classExpressionTranslator = classExpressionTranslator;
        this.classNodeTranslator = classNodeTranslator;
        this.consistencyTranslator = consistencyTranslator;
    }

    @Override
    public GetSubClassesResponse decode(GetSubClassesResponseMessage message) {
        Optional<KbQueryResult<NodeSet<OWLClass>>> result;
        if(message.getPresent()) {
            if(message.getConsistency() == Messages.Consistency.CONSISTENT) {
                Set<Node<OWLClass>> nodes = Sets.newHashSet();
                for(ClassHierarchyNode node : message.getSubClassesList()) {
                    nodes.add(classNodeTranslator.decode(node));
                }
                result = KbQueryResult.<NodeSet<OWLClass>>optionalOfValue(new OWLClassNodeSet(nodes));
            }
            else {
                result = KbQueryResult.optionalOfInconsistentKb();
            }
        }
        else {
            result = Optional.absent();
        }

        return new GetSubClassesResponse(
             kbIdTranslator.decode(message.getKbId()),
             kbDigestTranslator.decode(message.getKbDigest()), classExpressionTranslator.decode(message.getClassExpression()), result);
    }

    @Override
    public GetSubClassesResponseMessage encode(GetSubClassesResponse object) {
        GetSubClassesResponseMessage.Builder builder = GetSubClassesResponseMessage.newBuilder();
        builder.setKbId(kbIdTranslator.encode(object.getKbId()));
        builder.setKbDigest(kbDigestTranslator.encode(object.getKbDigest()));
        builder.setClassExpression(classExpressionTranslator.encode(object.getClassExpression()));
        builder.setPresent(object.getResult().isPresent());
        if(object.getResult().isPresent()) {
            KbQueryResult<NodeSet<OWLClass>> result = object.getResult().get();
            builder.setConsistency(consistencyTranslator.encode(result.getConsistency()));
            if(result.isConsistent()) {
                for(Node<OWLClass> node : result.getValue()) {
                    builder.addSubClasses(classNodeTranslator.encode(node));
                }
            }
        }

        return builder.build();
    }

    @Override
    public GetSubClassesResponse decode(byte[] bytes) throws IOException {
        return decode(GetSubClassesResponseMessage.parseFrom(bytes));
    }
}
