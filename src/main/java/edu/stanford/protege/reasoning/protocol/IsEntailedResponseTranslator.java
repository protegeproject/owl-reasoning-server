package edu.stanford.protege.reasoning.protocol;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.action.Consistency;
import edu.stanford.protege.reasoning.action.Entailed;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.KbQueryResult;
import edu.stanford.protege.reasoning.action.IsEntailedResponse;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;

import javax.inject.Inject;
import java.io.IOException;

import static edu.stanford.protege.reasoning.protocol.Messages.Axiom;
import static edu.stanford.protege.reasoning.protocol.Messages.IsEntailedResponseMessage;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 20/08/2014
 */
public class IsEntailedResponseTranslator implements Translator<IsEntailedResponse, Messages.IsEntailedResponseMessage> {

    private Translator<KbId, Messages.KbId> kbIdTranslator;

    private Translator<KbDigest, Messages.KbDigest> kbDigestTranslator;

    private Translator<OWLLogicalAxiom, Messages.Axiom> axiomTranslator;

    private EntailedTranslator entailedTranslator;

    private Translator<Consistency, Messages.Consistency> consistencyTranslator;

    @Inject
    public IsEntailedResponseTranslator(Translator<KbId, Messages.KbId> kbIdTranslator, Translator<KbDigest, Messages.KbDigest> kbDigestTranslator, Translator<OWLLogicalAxiom, Axiom> axiomTranslator, EntailedTranslator entailedTranslator, Translator<Consistency, Messages.Consistency> consistencyTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.kbDigestTranslator = kbDigestTranslator;
        this.axiomTranslator = axiomTranslator;
        this.entailedTranslator = entailedTranslator;
        this.consistencyTranslator = consistencyTranslator;
    }

    @Override
    public IsEntailedResponse decode(IsEntailedResponseMessage message) {
        Optional<KbQueryResult<Entailed>> result;
        if(message.getPresent()) {
            if(message.getConsistency() == Messages.Consistency.CONSISTENT) {
                result = KbQueryResult.optionalOfValue(entailedTranslator.decode(message.getResult()));
            }
            else {
                result = KbQueryResult.optionalOfInconsistentKb();
            }
        }
        else {
            result = Optional.absent();
        }
        return new IsEntailedResponse(
                kbIdTranslator.decode(message.getKbId()),
                kbDigestTranslator.decode(message.getKbDigest()),
                axiomTranslator.decode(message.getAxiom()),
                result);
    }

    @Override
    public IsEntailedResponseMessage encode(IsEntailedResponse response) {
        IsEntailedResponseMessage.Builder builder =  IsEntailedResponseMessage.newBuilder();

        builder.setKbId(kbIdTranslator.encode(response.getKbId()))
                .setAxiom(axiomTranslator.encode(response.getAxiom()))
                .setKbDigest(kbDigestTranslator.encode(response.getKbDigest()));

        if(response.getResult().isPresent()) {
            builder.setPresent(true);
            KbQueryResult<Entailed> result = response.getResult().get();
            builder.setConsistency(consistencyTranslator.encode(result.getConsistency()));
            if(result.isConsistent()) {
                builder.setResult(entailedTranslator.encode(result.getValue()));
            }
        }
        else {
            builder.setPresent(false);
        }

        return builder.build();
    }

    @Override
    public IsEntailedResponse decode(byte[] bytes) throws IOException {
        return decode(IsEntailedResponseMessage.parseFrom(bytes));
    }
}
