import re
from typing import List

# https://leetcode.com/problems/coupon-code-validator/

class ValidateCoupon:

    def validateCoupons(self, code: List[str], businessLine: List[str], isActive: List[bool]) -> List[str]:

        validBusinessLines = ["electronics", "grocery", "pharmacy", "restaurant"]
        pattern = re.compile(r'[^a-zA-Z0-9]+')
        templist = []

        # aggregates elements from multiple iterables
        for c, b, a in zip(code, businessLine, isActive):
            if c == "" or b not in validBusinessLines or a == False:
                continue

            if pattern.search(c):
                continue
            # add the tuple
            templist.append( (c, b))

        # let's sort it by business line, then code itself

        result = [sentry[0] for sentry in sorted(templist, key=lambda entry:(entry[1], entry[1]))]

        return result


# main
if __name__ == '__main__':
    vc = ValidateCoupon()
    code = ["TsCwKhY","qCeVkfb","ZGjX07H"] # expected: ["qCeVkfb","ZGjX07H","TsCwKhY"]
    busline = ["restaurant","electronics","pharmacy"]
    isActive = [True, True, True]


    result = vc.validateCoupons(code, busline, isActive)
    print(result)