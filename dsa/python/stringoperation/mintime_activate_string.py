"""
To compute the minimum time to ensure that a string contains k sets of valid substrings
# 3639
# https://leetcode.com/problems/minimum-time-to-activate-string/description/

SortedList is a handy tool to provide a sorted list and locate left and right indexes

"""
from typing import List
from sortedcontainers import SortedList

class MinTimeToActivateString3639:
    """
     A class to compute the minimum time to ensure that a string contains
     k sets of valid substrings
    """

    def mintime(self, s: str, order: List[int] , k: int) -> int:
        """
        Method to derive the time.
        :param s: the input string to break it into substring
        :param order: the array that indicates the position to break a string
        :param k: the expected number of valid substring
        :return:  the time to return k set of valid substring
        """
        # To speed up performance, filter out edge case first
        slen: int = len(s)
        max_substring: int = (slen * (slen + 1)) // 2

        if max_substring < k:
            return -1

        posmap: SortedList = SortedList([-1, slen])

        for time, startpos in enumerate(order):
            #
            #  [-1, 1, 3], startpos = 2, rightpos = 2
            #  [-1, 1, 3], startpos = 1, rightpos = 2
            #
            #
            rightpos: int = posmap.bisect_left(startpos)
            left: int = posmap[rightpos - 1]
            right: int = posmap[rightpos]

            left_combination: int = startpos - left
            right_combination: int = right - startpos
            k -= left_combination * right_combination
            if k <= 0:
                return time
            posmap.add(startpos)

        return -1

# main

if __name__ == '__main__':
    mt: MinTimeToActivateString3639 = MinTimeToActivateString3639()
    inputstring: str = "abc"
    orderlist: List[int] = [1, 0, 2]
    expectedk: int = 2
    expected: int = 0

    result: int = mt.mintime(inputstring, orderlist, expectedk)
    print(f"s={inputstring}, order={orderlist}, k={expectedk}, result={result}, "
          f"PASS={result == expected}")
