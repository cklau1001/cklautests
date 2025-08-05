from sortedcontainers import SortedList
from typing import List

#
#
# 3639
# https://leetcode.com/problems/minimum-time-to-activate-string/description/
#
# SortedList is a handy tool to provide a sorted list and locate left and right indexes
#
class MinTimeToActivateString3639:

    def minTime(self, s: str, order: List[int] , k: int) -> int:

        # To speed up performance, filter out edge case first
        slen: int = len(s)
        maxSubstring: int = (slen * (slen+1)) // 2

        if maxSubstring < k:
            return -1

        posMap: SortedList = SortedList([-1, slen])

        for time, startpos in enumerate(order):
            #
            #  [-1, 1, 3], startpos = 2, rightpos = 2
            #  [-1, 1, 3], startpos = 1, rightpos = 2
            #
            #
            rightpos: int = posMap.bisect_left(startpos)
            left: int = posMap[rightpos-1]
            right: int = posMap[rightpos]

            leftCombination: int = startpos - left
            rightCombination: int = right - startpos
            k -= leftCombination * rightCombination
            if k <= 0:
                return time
            posMap.add(startpos)

        return -1

# main

if __name__ == '__main__':
    mt: MinTimeToActivateString3639 = MinTimeToActivateString3639()
    s: str = "abc"
    order: List[int] = [1,0,2]
    k: int = 2
    expected: int = 0

    result: int = mt.minTime(s, order, k)
    print(f"s={s}, order={order}, k={k}, result={result}, PASS={result == expected}")