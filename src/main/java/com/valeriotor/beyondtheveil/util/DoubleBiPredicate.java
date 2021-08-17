package com.valeriotor.beyondtheveil.util;

import java.util.Objects;
import java.util.function.DoublePredicate;

@FunctionalInterface
public interface DoubleBiPredicate {

    boolean test(double value0, double value1);

    default DoubleBiPredicate and(DoubleBiPredicate other) {
        Objects.requireNonNull(other);
        return (value1, value2) -> test(value1, value2) && other.test(value1, value2);
    }

    default DoubleBiPredicate negate() {
        return (value1, value2) -> !test(value1, value2);
    }

    default DoubleBiPredicate or(DoubleBiPredicate other) {
        Objects.requireNonNull(other);
        return (value1, value2) -> test(value1, value2) || other.test(value1, value2);
    }
}
