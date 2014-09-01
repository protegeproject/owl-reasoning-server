package edu.stanford.protege.reasoning.protocol;

import com.google.common.collect.ImmutableList;
import com.google.protobuf.ByteString;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.ApplyChangesAction;
import org.semanticweb.owlapi.change.AxiomChangeData;

import javax.inject.Inject;
import java.io.IOException;

import static edu.stanford.protege.reasoning.protocol.Messages.ApplyChangesActionMessage;
import static edu.stanford.protege.reasoning.protocol.Messages.ChangeData;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 27/08/2014
 */
public class ApplyChangesActionTranslator implements Translator<ApplyChangesAction, Messages.ApplyChangesActionMessage> {

    private Translator<KbId, Messages.KbId> kbIdTranslator;

    private BinaryOWLHelper binaryOWLHelper;

    @Inject
    public ApplyChangesActionTranslator(Translator<KbId, Messages.KbId> kbIdTranslator, BinaryOWLHelper binaryOWLHelper) {
        this.kbIdTranslator = kbIdTranslator;
        this.binaryOWLHelper = binaryOWLHelper;
    }

    @Override
    public ApplyChangesAction decode(ApplyChangesActionMessage message) {
        ImmutableList.Builder<AxiomChangeData> changeData = ImmutableList.builder();
        for(ChangeData data : message.getChangesList()) {
            ByteString serialization = data.getSerialization();
            changeData.add(binaryOWLHelper.decodeChangeData(serialization));
        }
        return new ApplyChangesAction(kbIdTranslator.decode(message.getKbId()), changeData.build());
    }

    @Override
    public ApplyChangesActionMessage encode(ApplyChangesAction object) {
        ApplyChangesActionMessage.Builder builder = ApplyChangesActionMessage.newBuilder()
                .setKbId(kbIdTranslator.encode(object.getKbId()));
        for(AxiomChangeData data : object.getChangeData()) {
            Messages.ChangeData changeData = Messages.ChangeData.newBuilder()
                    .setSerialization(binaryOWLHelper.encodeChangeData(data))
                    .build();
            builder.addChanges(changeData);
        }
        return builder.build();
    }

    @Override
    public ApplyChangesAction decode(byte[] bytes) throws IOException {
        return decode(ApplyChangesActionMessage.parseFrom(bytes));
    }
}
