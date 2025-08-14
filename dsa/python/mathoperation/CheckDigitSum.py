
# https://leetcode.com/problems/group-anagrams
class CheckDigitSum:

    def checkDivisibility(self, n: int) -> bool:

        temp: int = n
        digit_sum, digit_product = 0, 1

        while (temp > 0):
            divide: int = temp // 10
            mod: int = temp % 10

            digit_sum += mod
            digit_product *= mod

            temp //= 10

        result = n % (digit_sum + digit_product) == 0
        return result


# main

if __name__ == '__main__':

    input: int = 99
    cs: CheckDigitSum = CheckDigitSum()

    result: bool = cs.checkDivisibility(input)

    print(f"input={input}, result={result}")
