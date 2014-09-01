package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.ReplaceAxiomsAction;
import edu.stanford.protege.reasoning.action.ReplaceAxiomsResponse;
import org.semanticweb.binaryowl.chunk.ChunkUtil;

import javax.inject.Inject;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class ReplaceAxiomsCodec extends AbstractCodec<ReplaceAxiomsAction, ReplaceAxiomsResponse> {

    @Inject
    public ReplaceAxiomsCodec(ReplaceAxiomsActionTranslator actionTranslator, ReplaceAxiomsResponseTranslator responseTranslator) {
        super(ChunkUtil.toInt("rplx"),
                ReplaceAxiomsAction.class,
                ReplaceAxiomsResponse.class,
                actionTranslator, responseTranslator);
    }
}
