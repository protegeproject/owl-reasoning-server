package edu.stanford.protege.reasoning.protocol;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.KbQueryResult;
import edu.stanford.protege.reasoning.action.GetInstancesResponse;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLNamedIndividualNode;
import org.semanticweb.owlapi.reasoner.impl.OWLNamedIndividualNodeSet;

import java.io.IOException;
import java.util.Set;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 27/08/2014
 */
public class GetInstancesResponseTranslator implements Translator<GetInstancesResponse,
        Messages.GetInstancesResponseMessage> {

    private Translator<KbId, Messages.KbId> kbIdTranslator;

    private Translator<KbDigest, Messages.KbDigest> kbDigestTranslator;

    private Translator<OWLClassExpression, Messages.ClassExpression> classExpressionTranslator;

    private Translator<OWLNamedIndividual, Messages.IndividualName> individualNameTranslator;

    @Inject
    public GetInstancesResponseTranslator(Translator<KbId, Messages.KbId> kbIdTranslator,
                                          Translator<KbDigest, Messages.KbDigest> kbDigestTranslator,
                                          Translator<OWLClassExpression,
                                                  Messages.ClassExpression> classExpressionTranslator,
                                          Translator<OWLNamedIndividual,
                                                  Messages.IndividualName> individualNameTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.kbDigestTranslator = kbDigestTranslator;
        this.classExpressionTranslator = classExpressionTranslator;
        this.individualNameTranslator = individualNameTranslator;
    }

    @Override
    public GetInstancesResponse decode(Messages.GetInstancesResponseMessage message) {
        Optional<KbQueryResult<NodeSet<OWLNamedIndividual>>> result;
        if (message.getPresent()) {
            if (message.getConsistency() == Messages.Consistency.CONSISTENT) {
                Set<Node<OWLNamedIndividual>> individuals = Sets.newHashSet();
                for (Messages.IndividualName individualName : message.getInstancesList()) {
                    OWLNamedIndividual individual = individualNameTranslator.decode(individualName);
                    individuals.add(new OWLNamedIndividualNode(individual));
                }
                result = KbQueryResult.<NodeSet<OWLNamedIndividual>>optionalOfValue(new OWLNamedIndividualNodeSet(
                        individuals));
            }
            else {
                result = KbQueryResult.optionalOfInconsistentKb();
            }
        }
        else {
            result = Optional.absent();
        }
        return new GetInstancesResponse(kbIdTranslator.decode(message.getKbId()),
                                        kbDigestTranslator.decode(message.getKbDigest()),
                                        classExpressionTranslator.decode(message.getClassExpression()),
                                        result);
    }

    @Override
    public Messages.GetInstancesResponseMessage encode(GetInstancesResponse object) {
        Messages.GetInstancesResponseMessage.Builder builder = Messages.GetInstancesResponseMessage.newBuilder();
        builder.setKbId(kbIdTranslator.encode(object.getKbId()));
        builder.setKbDigest(kbDigestTranslator.encode(object.getKbDigest()));
        builder.setClassExpression(classExpressionTranslator.encode(object.getClassExpression()));
        builder.setPresent(object.getResult().isPresent());
        if (object.getResult().isPresent()) {
            KbQueryResult<NodeSet<OWLNamedIndividual>> result = object.getResult().get();
            if (result.isConsistent()) {
                builder.setConsistency(Messages.Consistency.CONSISTENT);
                NodeSet<OWLNamedIndividual> nodeSet = result.getValue();
                for (OWLNamedIndividual ind : nodeSet.getFlattened()) {
                    builder.addInstances(individualNameTranslator.encode(ind));
                }
            }
            else {
                builder.setConsistency(Messages.Consistency.INCONSISTENT);
            }
        }
        return builder.build();
    }

    @Override
    public GetInstancesResponse decode(byte[] bytes) throws IOException {
        return decode(Messages.GetInstancesResponseMessage.parseFrom(bytes));
    }
}
