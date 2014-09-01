package edu.stanford.protege.reasoning;

import com.google.common.base.Objects;

import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 *
 * Represents an identifier for a knowledge base.
 */
public class KbId {

    private static final AtomicInteger counter = new AtomicInteger();

    private String id;

    /**
     * Constructs a KbId that has an id as specified.
     * @param id The id.  Not {@code null}.
     * @throws NullPointerException if {@code id} is {@code null}.
     */
    public KbId(String id) {
        this.id = checkNotNull(id);
    }

    /**
     * Gets the lexical form of this KbId.
     * @return The lexical form of this KbId.  Not {@code null}.
     */
    public String getLexicalForm() {
        return id;
    }

    @Override
    public int hashCode() {
        return "KbId".hashCode() + id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof KbId)) {
            return false;
        }
        KbId other = (KbId) o;
        return this.id.equals(other.id);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper("KbId").addValue(id).toString();
    }
}
