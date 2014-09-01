package edu.stanford.protege.reasoning.protocol;

import com.google.common.collect.ImmutableList;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.ReplaceAxiomsAction;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 28/08/2014
 */
public class ReplaceAxiomsActionTranslator implements Translator<ReplaceAxiomsAction, Messages.ReplaceAxiomsActionMessage> {

    private final Translator<KbId, Messages.KbId> kbIdTranslator;

    private final Translator<OWLLogicalAxiom, Messages.Axiom> axiomTranslator;

    @Inject
    public ReplaceAxiomsActionTranslator(Translator<KbId, Messages.KbId> kbIdTranslator, Translator<OWLLogicalAxiom, Messages.Axiom> axiomTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.axiomTranslator = axiomTranslator;
    }

    @Override
    public ReplaceAxiomsAction decode(Messages.ReplaceAxiomsActionMessage message) {
        ImmutableList.Builder<OWLAxiom> builder = ImmutableList.builder();
        for(Messages.Axiom ax : message.getAxiomsList()) {
            builder.add(axiomTranslator.decode(ax));
        }
        return new ReplaceAxiomsAction(
                kbIdTranslator.decode(message.getKbId()),
                builder.build()
        );
    }

    @Override
    public Messages.ReplaceAxiomsActionMessage encode(ReplaceAxiomsAction object) {
        Messages.ReplaceAxiomsActionMessage.Builder builder = Messages.ReplaceAxiomsActionMessage.newBuilder();
        builder.setKbId(kbIdTranslator.encode(object.getKbId()));
        for(OWLAxiom ax : object.getAxioms()) {
            builder.addAxioms(axiomTranslator.encode((OWLLogicalAxiom) ax));
        }
        return builder.build();
    }

    @Override
    public ReplaceAxiomsAction decode(byte[] bytes) throws IOException {
        return decode(Messages.ReplaceAxiomsActionMessage.parseFrom(bytes));
    }
}
