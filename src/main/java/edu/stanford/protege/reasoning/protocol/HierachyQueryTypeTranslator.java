package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.HierarchyQueryType;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 27/08/2014
 */
public class HierachyQueryTypeTranslator implements Translator<HierarchyQueryType, Messages.HierarchyQueryType> {

    @Override
    public HierarchyQueryType decode(Messages.HierarchyQueryType message) {
        return message == Messages.HierarchyQueryType.DIRECT ? HierarchyQueryType.DIRECT : HierarchyQueryType.INDIRECT;
    }

    @Override
    public Messages.HierarchyQueryType encode(HierarchyQueryType object) {
        return object == HierarchyQueryType.DIRECT ? Messages.HierarchyQueryType.DIRECT : Messages.HierarchyQueryType.INDIRECT;
    }

    @Override
    public HierarchyQueryType decode(byte[] bytes) throws IOException {
        throw new RuntimeException();
    }
}
