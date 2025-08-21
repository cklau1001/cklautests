import unittest
from typing import List
from stringoperation.validate_coupon import ValidateCoupon

class TestValidateCoupon(unittest.TestCase):

    def setUp(self):
        self.vc: ValidateCoupon = ValidateCoupon()

    def test_validate_without_active_code(self):

        code: List[str] = ["TsCwKhY", "qCeVkfb", "ZGjX07H"]
        busline: List[str] = ["restaurant", "electronics", "pharmacy"]
        is_active: List[bool] = [False, False, False]

        result: List[str] = self.vc.validate_coupons(code, busline, is_active)
        self.assertEqual(0, len(result))

    def test_validate_invalid_busline(self):

        code: List[str] = ["GROCERY15", "ELECTRONICS_50", "DISCOUNT10"]
        busline: List[str] = ["grocery", "electronics", "invalid"]
        is_active: List[bool] = [True, True, True]

        result: List[str] = self.vc.validate_coupons(code, busline, is_active)
        self.assertEqual(2, len(result))
        self.assertEqual(code[1], result[0])
        self.assertEqual(code[0], result[1])

    def test_validate_with_sorted_result_by_busline(self):

        code: List[str] = ["TsCwKhY", "qCeVkfb", "ZGjX07H"]
        busline: List[str] = ["restaurant", "electronics", "pharmacy"]
        is_active: List[bool] = [True, True, True]

        result: List[str] = self.vc.validate_coupons(code, busline, is_active)
        self.assertEqual(len(is_active), len(result))
        self.assertEqual(code[1], result[0])
        self.assertEqual(code[2], result[1])
        self.assertEqual(code[0], result[2])

    def test_validate_with_empty_coupon(self):

        code: List[str] = []
        busline: List[str] = ["restaurant"]
        is_active: List[bool] = [True]

        result: List[str] = self.vc.validate_coupons(code, busline, is_active)
        self.assertEqual(0, len(result))

    def test_validate_with_invalid_code(self):

        code: List[str] = ["invalid-code-name"]
        busline: List[str] = ["restaurant"]
        is_active: List[bool] = [True]

        result: List[str] = self.vc.validate_coupons(code, busline, is_active)
        self.assertEqual(0, len(result))