package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.IsEntailedAction;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 20/08/2014
 */
public class IsEntailedActionTranslator implements Translator<IsEntailedAction, Messages.IsEntailedActionMessage> {

    private final Translator<KbId, Messages.KbId> kbIdTranslator;

    private final Translator<OWLLogicalAxiom, Messages.Axiom> axiomTranslator;


    @Inject
    public IsEntailedActionTranslator(Translator<KbId, Messages.KbId> kbIdTranslator,
                                      Translator<OWLLogicalAxiom, Messages.Axiom> axiomTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.axiomTranslator = axiomTranslator;
    }

    @Override
    public IsEntailedAction decode(Messages.IsEntailedActionMessage message) {
        OWLLogicalAxiom ax = axiomTranslator.decode(message.getAxiom());
        KbId decode = kbIdTranslator.decode(message.getKbId());
        return new IsEntailedAction(decode, ax);
    }

    @Override
    public Messages.IsEntailedActionMessage encode(IsEntailedAction object) {
        Messages.IsEntailedActionMessage.Builder builder = Messages.IsEntailedActionMessage.newBuilder();
        builder.setKbId(kbIdTranslator.encode(object.getKbId()));
        builder.setAxiom(axiomTranslator.encode(object.getAxiom()));
        return builder.build();
    }

    @Override
    public IsEntailedAction decode(byte[] bytes) throws IOException {
        return decode(Messages.IsEntailedActionMessage.parseFrom(bytes));
    }
}
