package edu.stanford.protege.reasoning.action;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class ActionType<H extends ActionHandler> {

    private static int counter = 0;

    private int typeId;

    private ActionType() {
        counter++;
        typeId = counter;
    }

    /**
     * A convenience method for creating a fresh {@link ActionType}.
     * @param <H> The type of handler.
     * @return The action type.
     */
    public static <H extends ActionHandler> ActionType<H> create() {
        return new ActionType<H>();
    }

    @Override
    public int hashCode() {
        return "ActionType".hashCode() + typeId;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof ActionType)) {
            return false;
        }
        ActionType other = (ActionType) o;
        return this.typeId == other.typeId;
    }
}
