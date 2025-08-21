import unittest

from mathoperation.check_digit_sum import CheckDigitSum

class TestCheckDigitSum(unittest.TestCase):

    def setUp(self):
        self.check_digit_sum: CheckDigitSum = CheckDigitSum()

    def test_digit_sum_positive_result(self):
        num: int = 99
        result: bool = False

        result = self.check_digit_sum.check_divisibility(num)
        self.assertTrue(result)

    def test_digit_sum_negative_result(self):
        num: int = 98
        result: bool = True

        result = self.check_digit_sum.check_divisibility(num)
        self.assertFalse(result)


