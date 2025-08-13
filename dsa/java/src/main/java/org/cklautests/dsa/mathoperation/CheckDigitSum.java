package org.cklautests.dsa.mathoperation;

/*

3622
https://leetcode.com/problems/check-divisibility-by-digit-sum-and-product/description/

 */
public class CheckDigitSum {

    public boolean checkDivisibilityByModus(int n) {
        /*
        we try to get each digit by dividing n by 10 continuously.

        132 / 10 = 11; 132 % 10 = 2   => 2
           13 / 10 = 1; 13 % 10 = 3       3
             1 / 10 = 0;  1 % 10 = 1         1
         */


        int temp = n;
        int digitSum = 0, digitProduct = 1;

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

        result = n % (digitSum + digitProduct) == 0 ;
        return result;

    }

    public static void main(String[] args) {

        CheckDigitSum cs = new CheckDigitSum();
        int input = 99; // expected = true
        boolean result = false;

        result = cs.checkDivisibilityByModus(input);
        System.out.printf("input=%s, result=%s", input, result);

    }

}
