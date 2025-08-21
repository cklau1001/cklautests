package org.cklautests.dsa.mathoperation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckDigitSumTest {

    final CheckDigitSum cs = new CheckDigitSum();

    @Test
    void testDigitSumPositiveResult() {
        int num = 99;
        boolean result;

        result = cs.checkDivisibilityByStandardApproach(num);
        assertThat(result).as("[1] Check Digit Sum by standard approach with positive result").isTrue();

        result = cs.checkDivisibilityByModus(num);
        assertThat(result).as("[2] Check Digit Sum by modus with positive result").isTrue();

    }

    @Test
    void testDigitSumNegativeResult() {
        int num = 98;
        boolean result;

        result = cs.checkDivisibilityByStandardApproach(num);
        assertThat(result).as("[1] Check Digit Sum by standard approach with negative result").isFalse();

        result = cs.checkDivisibilityByModus(num);
        assertThat(result).as("[2] Check Digit Sum by modus with negative result").isFalse();

    }
}
