import unittest
from typing import List

from stringoperation.mintime_activate_string import MinTimeToActivateString3639

class TestMinTimeToActivateString3639(unittest.TestCase):

    def setUp(self):
        self.mt: MinTimeToActivateString3639 = MinTimeToActivateString3639()

    def test_mintime_normal_input(self):
        s: str = "cat"
        order: List[int] = [0, 2, 1]
        k: int = 6
        expected: int = 2

        result: int = self.mt.mintime(s, order, k)
        self.assertEqual(expected, result)

    def test_mintime_big_k(self):
        s: str = "abc"
        order: List[int] = [1, 0, 2]
        k: int = 10
        expected: int = -1

        result: int = self.mt.mintime(s, order, k)
        self.assertEqual(expected, result)

    def test_mintime_empty_order(self):
        s: str = "abc"
        order: List[int] = []
        k: int = 2
        expected: int = -1

        result: int = self.mt.mintime(s, order, k)
        self.assertEqual(expected, result)
