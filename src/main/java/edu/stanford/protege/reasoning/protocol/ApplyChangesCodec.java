package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.ApplyChangesAction;
import edu.stanford.protege.reasoning.action.ApplyChangesResponse;
import org.semanticweb.binaryowl.chunk.ChunkUtil;

import javax.inject.Inject;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class ApplyChangesCodec extends AbstractCodec<ApplyChangesAction, ApplyChangesResponse> {

    @Inject
    public ApplyChangesCodec(ApplyChangesActionTranslator actionTranslator, ApplyChangesResponseTranslator responseTranslator) {
        super(
                ChunkUtil.toInt("aply"),
                ApplyChangesAction.class, ApplyChangesResponse.class,
                actionTranslator, responseTranslator);
    }
}
