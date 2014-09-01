package edu.stanford.protege.reasoning;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.action.Consistency;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/08/2014
 *
 * Wraps the result for a query that relies on the knowledge base that is queried being consistent.  If the query in
 * question is submitted to an inconsistent knowledge base then the result value will not be filled in.
 */
public final class KbQueryResult<R> {

    private static final KbQueryResult<?> INCONSISTENT_RESULT = new KbQueryResult<>();

    private final Consistency consistency;

    private final R result;

    /**
     * Gets the result for a consistent kb.
     * @param result The result. Not {@code null}.
     */
    private KbQueryResult(R result) {
        this.result = checkNotNull(result);
        this.consistency = Consistency.CONSISTENT;
    }

    /**
     * Gets the result for an inconsistent kb.
     */
    private KbQueryResult() {
        this.consistency = Consistency.INCONSISTENT;
        this.result = null;
    }

    /**
     * Gets the present value of an inconsistent knowledge base result.
     * @param <R> The type of the result.
     * @return A present optional of the inconsistent kb query result.  Not {@code null}.
     */
    public static <R> Optional<KbQueryResult<R>> optionalOfInconsistentKb() {
        KbQueryResult<R> result = ofInconsistentKb();
        return Optional.of(result);
    }

    /**
     * Gets the KbQueryResult corresponding to the result for a query on an inconsistent knowledge base.
     * @param <R> The result type.
     * @return The KbQueryResult for a query submitted to an inconsistent knowledge base.  Not {@code null}.
     */
    @SuppressWarnings("unchecked")
    public static <R> KbQueryResult<R> ofInconsistentKb() {
        return (KbQueryResult<R>) INCONSISTENT_RESULT;
    }


    /**
     * Gets the KbQueryResult for the specified result value.
     * @param resultValue The result value.  Not {@code null}.
     * @param <R> The result type.
     * @return A KbQueryResult that wraps the specified result value. {@link #getConsistency()} will return
     * {@link Consistency#CONSISTENT}. Not {@code null}.
     * @throws NullPointerException if {@code resultValue} is {@code null}.
     */
    public static <R> KbQueryResult<R> ofValue(R resultValue) {
        return new KbQueryResult<>(checkNotNull(resultValue));
    }

    /**
     * Gets the present value of the specified result value.  This method is a convenience method for wrapping the
     * return value of {@link KbQueryResult#ofValue(Object)} in an {@link Optional}.
     * @param resultValue The value of the result.  Not {@code null}.
     * @param <R> The result value type.
     * @return A present optional of the specified result value.  Not {@code null}.
     * @throws NullPointerException if {@code resultValue} is {@code null}.
     */
    public static <R> Optional<KbQueryResult<R>> optionalOfValue(R resultValue) {
        return Optional.of(ofValue(resultValue));
    }


    /**
     * Gets the consistency of the knowledge base that was queried.
     * @return The consistency.  Not {@code null}.
     */
    public Consistency getConsistency() {
        return consistency;
    }

    /**
     * A convenience method which tests whether the value returned by {@link #getConsistency()} is equal to
     * {@link Consistency#CONSISTENT}.
     * @return {@code true} is {@link #getConsistency()} returns a value equal to {@link Consistency#CONSISTENT},
     * otherwise {@code false}.
     */
    public boolean isConsistent() {
        return consistency == Consistency.CONSISTENT;
    }

    /**
     * Gets the result, which will only be present if it is the result from a query on a consistent knowledge base.
     * @return The result.
     * @throws IllegalStateException is this result represents the result of a query on an inconsistent knowledge base.
     * That is, if {@link #getConsistency()} returns {@link Consistency#INCONSISTENT}.
     */
    public R getValue() {
        if(result == null) {
            throw new IllegalStateException("Response is for an inconsistent KB");
        }
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("KbQueryResult")
                .addValue(consistency)
                .add("result", result)
                .toString();
    }

    @Override
    public int hashCode() {
        return "KbQueryResult".hashCode() + Objects.hashCode(consistency, result);
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof KbQueryResult)) {
            return false;
        }
        KbQueryResult other = (KbQueryResult) o;
        return this.consistency.equals(other.consistency) && Objects.equal(this.result, other.result);
    }

}
