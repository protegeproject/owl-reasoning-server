package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.Progress;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ProgressTranslator implements Translator<Progress, Messages.Progress> {

    @Override
    public Progress decode(Messages.Progress message) {
        if(message.getDeterminate()) {
            return Progress.from(message.getInitialValue()).to(message.getFinalValue()).withValue(message.getValue());
        }
        else {
            return Progress.indeterminate();
        }
    }

    @Override
    public Progress decode(byte[] bytes) throws IOException {
        return decode(Messages.Progress.parseFrom(bytes));
    }

    @Override
    public Messages.Progress encode(Progress object) {
        Messages.Progress.Builder builder = Messages.Progress.newBuilder();
        if(object.isIndeterminate()) {
            builder.setDeterminate(false);
        }
        else {
            builder.setDeterminate(true);
            builder.setInitialValue(object.getInitialValue());
            builder.setFinalValue(object.getFinalValue());
            builder.setValue(object.getValue());
        }
        return builder.build();
    }
}
