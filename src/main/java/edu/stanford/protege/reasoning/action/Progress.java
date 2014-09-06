package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public abstract class Progress {

    private final Optional<Integer> initialValue;

    private final Optional<Integer> finalValue;

    private final Optional<Integer> value;

    protected Progress(Optional<Integer> initialValue, Optional<Integer> finalValue, Optional<Integer> value) {
        this.initialValue = checkNotNull(initialValue);
        this.finalValue = checkNotNull(finalValue);
        this.value = checkNotNull(value);
    }

    public abstract boolean isIndeterminate();

    /**
     * Gets the initial progress value.
     * @return An integer representing the progress initial value.
     * @throws IllegalStateException if this progress is indeterminate.
     */
    public int getInitialValue() {
        return initialValue.get();
    }


    /**
     * Gets the final progress value.  This may be greater than, less than or equal to the initial value.
     * @return An integer representing the progress final value.
     * @throws IllegalStateException if this progress is indeterminate.
     */
    public int getFinalValue() {
        return finalValue.get();
    }


    /**
     * Gets the current progress value.
     * @return An integer representing the progress initial value.  This will lie between the initial and final
     * value inclusive.
     * @throws IllegalStateException if this progress is indeterminate.
     */
    public int getValue() {
        return value.get();
    }

    /**
     * Gets the value expressed as a percentage.
     * @return The value expressed as a percentage. This will be between 0 and 100 inclusive.  If the initial
     * and final values are equal then the value will be 100.
     */
    public int getPercentageValue() {
        if(initialValue.equals(finalValue)) {
            return 100;
        }
        return (int) (100.0 * (value.get() - initialValue.get()) / (finalValue.get() - initialValue.get()));
    }


    public static Progress indeterminate() {
        return new IndeterminateProgress();
    }


    public static ProgressBuilderInitial from(int initialValue) {
        return new ProgressBuilderInitial(initialValue);
    }

    public static class ProgressBuilderInitial {

        private int initialValue;

        public ProgressBuilderInitial(int initialValue) {
            this.initialValue = initialValue;
        }

        public ProgressBuilderFinal to(int value) {
            return new ProgressBuilderFinal(initialValue, value);
        }
    }

    public static class ProgressBuilderFinal {

        private int initialValue;

        private int finalValue;

        public ProgressBuilderFinal(int initialValue, int finalValue) {
            this.initialValue = initialValue;
            this.finalValue = finalValue;
        }

        public Progress withValue(int value) {
            return new DeterminateProgress(initialValue, finalValue, value);
        }
    }


    private static final class DeterminateProgress extends Progress {

        public DeterminateProgress(int min, int max, int current) {
            super(Optional.of(min), Optional.of(max), Optional.of(current));
            checkArgument(
                    (min < max && min <= current && current <= max)
                    || (min > max && max <= current && current <= min)
                    || (min == max && max == current)
            );
        }

        @Override
        public boolean isIndeterminate() {
            return false;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper("Progress")
                          .add("value", getValue() + "(" + getPercentageValue() + ")")
                          .add("from", getInitialValue())
                          .add("to", getFinalValue())
                          .toString();
        }
    }

    private static final class IndeterminateProgress extends Progress {

        public IndeterminateProgress() {
            super(Optional.<Integer>absent(), Optional.<Integer>absent(), Optional.<Integer>absent());
        }

        @Override
        public boolean isIndeterminate() {
            return true;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper("Progress")
                          .addValue("Indeterminate")
                          .toString();
        }
    }


}
