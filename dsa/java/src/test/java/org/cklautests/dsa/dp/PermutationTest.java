package org.cklautests.dsa.dp;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class PermutationTest {

    final Permutation permutation = new Permutation();
    final int[] input = new int[]{1, 2, 3};
    final int expectedPermutation = 6; // total permutation = 3 * 2 * 1 or 3!

    @Test
    void testPermutationbyRec() {

        List<List<Integer>> result = permutation.permutateByRec(input);
        assertThat(result.size())
                .as("Test unique permutation by recursive")
                .isEqualTo(expectedPermutation);
    }

    @Test
    void testPermutationBySwap() {
        List<List<Integer>> result = permutation.permutateBySwap(input);
        assertThat(result.size())
                .as("Test unique permutation by adjacent node swap")
                .isEqualTo(expectedPermutation);
    }

    @Test
    void testPermutation47() {
        int[] duplicateInput = new int[]{1, 1, 2};
        int expectedResult = 3;
        List<List<Integer>> result = permutation.permuteUnique47(duplicateInput, true);
        assertThat(result.size())
                .as("Test duplicate input using frequency map")
                .isEqualTo(expectedResult);

        result = permutation.permuteUnique47(duplicateInput, false);
        assertThat(result.size())
                .as("Test duplicate input using hashset")
                .isEqualTo(expectedResult);

    }

}
