package org.cklautests.dsa.mathoperation;

/*

3622
https://leetcode.com/problems/check-divisibility-by-digit-sum-and-product/description/

 */
public class CheckDigitSum {

    /**
     * Check if the input number (n) can be divided by the
     *  sum and product of each digit using mod 10 approach.
     * @param n - the input number
     * @return - return the boolean result
     */
    public boolean checkDivisibilityByModus(int n) {
        /*
          we try to get each digit by dividing n by 10 continuously.
          use primitive type to derive the answer, more efficient than standard approach.

        132 / 10 = 11; 132 % 10 = 2   => 2
           13 / 10 = 1; 13 % 10 = 3       3
             1 / 10 = 0;  1 % 10 = 1         1
         */


        int temp = n;
        int digitSum = 0;
        int digitProduct = 1;

        while (temp > 0) {
            int divide = temp / 10;
            int mod = temp % 10;

            digitSum += mod;
            digitProduct *= mod;

            temp /= 10;
        }

        boolean result = n % (digitSum + digitProduct) == 0;
        return result;
    }

    /**
     * Check if the input number (n) can be divided by the
     *   sum and product of each digit, by extracting each character
     *   that derive the sum and product.
     * @param n - the input number
     * @return - the boolean result if the number can be divided.
     */
    public boolean checkDivisibilityByStandardApproach(int n) {

        boolean result = false;

        char[] charlist = String.valueOf(n).toCharArray();

        // digit sum
        long digitSum = 0;
        long digitProduct = 1;

        for (char c: charlist) {
            digitSum += c - '0';
            digitProduct *= c - '0';
        }

        result = n % (digitSum + digitProduct) == 0;
        return result;

    }

}
