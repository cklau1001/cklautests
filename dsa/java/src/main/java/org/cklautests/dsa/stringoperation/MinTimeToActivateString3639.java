package org.cklautests.dsa.stringoperation;

import java.util.Arrays;
import java.util.Objects;
import java.util.TreeSet;

/*

3639
https://leetcode.com/problems/minimum-time-to-activate-string/description/

 */
public class MinTimeToActivateString3639 {

    /**
     * Cacluate the minimum time to divide a string into k substrings.
     *
     * @param s
     * @param order
     * @param k
     * @return the minimum time to divide the input string into k substrings.
     */
    public int minTime(String s, int[] order, int k) {

        /* edge condition
             for any string, max number of substring = n * (n+1) / 2
             A-B-C          length        sub-total
               A-B-C :          3            1
               A-B, B-C:        2            2
               A, B, C          1            3      => 1 + 2 + 3 = 6 (arithmetic progression)
         */
        long slen = s.length();
        long maxSubstring = (slen + (slen + 1)) / 2;
        if (maxSubstring < k) {  // no need to compute if maxSubstring smaller than target(k)
            return -1;
        }

        // A treeSet can create an increasing monotonic queue
        TreeSet<Integer> posMap = new TreeSet<>();

        /*
           initialize posMap with position at -1 and order.length, effectively no * in the string

         */
        posMap.add(-1);
        posMap.add(s.length());
        long totalValidString = 0;
        /*
           0 1 *
             0-1-* (3), 1-* (2), * (1) => 3  => (leftCount+1)
           * 2 3
             *-2-3 (3), *-2 (2), * (1) => 3 => (rightCount+1)
          combine them together => 0 1 * 2 3
            total combination = 3 * 3 = 9

            0  1  2  3  4
          -1   *           5

            0  1  2  3  4
          -1   *  *        5
           0*, 0**, **, *, **3, **34, *3  => 2 * 4 + 1 * 3
           0**3, 0**34, *34, *

         */
        for (int time = 0; time < order.length; time++) {
            int starpos = order[time];
            // requireNonNull() to confirm boxing will not throw NPE,
            // but it will slow down the execution
            int left = Objects.requireNonNull(posMap.floor(starpos));
            int right = Objects.requireNonNull(posMap.ceiling(starpos));

            // leftCount = starpos - left - 1, leftCombination = leftCount+1
            int leftCombination = (starpos - left);
            int rightCombination = (right - starpos);

            // need to cast long to cater for very large values
            totalValidString += ((long) leftCombination * rightCombination);
            if (totalValidString >= k) {
                return time;
            }

            posMap.add(starpos);
        }

        return -1;
    }

    /**
     * The entry point of the program.
     *
     * @param args - command line arguments
     */
    public static void main(String[] args) {

        MinTimeToActivateString3639 mt = new MinTimeToActivateString3639();
        String s = "abc";
        int[] order = new int[]{1, 0, 2};
        int k = 2;
        int expected = 0;

        int result = mt.minTime(s, order, k);

        System.out.printf("s=%s, order=%s, k=%s, result=%s, PASS=%s",
                s, Arrays.toString(order), k, result, result == expected);

    }

}
