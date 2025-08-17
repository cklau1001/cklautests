package org.cklautests.dsa.stringoperation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MinTimeToActivateString3639Test {

    final MinTimeToActivateString3639 mt = new MinTimeToActivateString3639();

    @Test
    void testminTimeWithNormalInput() {
        String s = "cat";
        int[] order = new int[]{0, 2, 1};
        int k = 6;
        int expected = 2;

        int result = mt.minTime(s, order, k);
        assertThat(result).as("Test minTime normal input").isEqualTo(expected);

    }

    @Test
    void testminTimeWithBigK() {

        String s = "abc";
        int[] order = new int[]{1, 0, 2};
        int k = 10;  // expected substring combination too large for the input string (abc)
        int expected = -1;

        int result = mt.minTime(s, order, k);
        assertThat(result).as("Test minTime big k").isEqualTo(expected);

    }

    @Test
    void testminTimeWithEmptyOrder() {

        String s = "abc";
        int[] order = new int[]{};
        int k = 2;
        int expected = -1;

        int result = mt.minTime(s, order, k);
        assertThat(result).as("Test minTime with empty order").isEqualTo(expected);

    }

}
