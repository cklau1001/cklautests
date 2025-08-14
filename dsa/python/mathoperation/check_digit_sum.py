"""
Program to check if an input integer can be divided by the sum and product of each digits.
https://leetcode.com/problems/group-anagrams
"""
from sortedcontainers import SortedList
class CheckDigitSum:
    """

        The class to check if the input integer (n) can be divided by the sum and product of
        each digits.

    """
    def check_divisibility(self, n: int) -> bool:
        """
        Check if the input integer (n) can be divided by the sum and product of each digits.
        :param n: input integer
        :return:  result in boolean
        """

        temp: int = n
        digit_sum, digit_product = 0, 1

        while temp > 0:
            # divide: int = temp // 10
            mod: int = temp % 10

            digit_sum += mod
            digit_product *= mod

            temp //= 10

        return n % (digit_sum + digit_product) == 0


# main

if __name__ == '__main__':

    inputnum: int = 99
    cs: CheckDigitSum = CheckDigitSum()

    result: bool = cs.check_divisibility(inputnum)

    print(f"input={inputnum}, result={result}")
