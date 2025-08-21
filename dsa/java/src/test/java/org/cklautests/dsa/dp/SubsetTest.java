package org.cklautests.dsa.dp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class SubsetTest {

    final Subset subset = new Subset();
    final int[] input = new int[] {1, 2, 3};
    final int expectedCombination = 8; // 2^3

    @Test
    void testsubsetsByRec() {
        List<List<Integer>> result = subset.subsetsByRec(input);
        System.out.println(result);
        assertThat(result.size())
                .as("Test unique subset by recursive method")
                .isEqualTo(expectedCombination);
    }

    @Test
    void testsubsetsByBitwise() {
        List<List<Integer>> result = subset.subsetsByBit(input);
        System.out.println(result);
        assertThat(result.size())
                .as("Test unique subset by bitwise operation")
                .isEqualTo(expectedCombination);
    }

    @Test
    void testDupSubsetsByRec() {
        int[] duplicateInput = new int[] {1, 2, 2};
        List<List<Integer>> result = subset.subsets2WithDup(duplicateInput);
        int expectedResult = 6;
        System.out.println(result);
        assertThat(result.size())
                .as("Test duplicate subset by recursive method")
                .isEqualTo(expectedResult);
    }

}
