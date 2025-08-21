"""
 To validate incoming coupons based on the business line and active status.

  3606
  https://leetcode.com/problems/coupon-code-validator/

"""
import re
from typing import List


class ValidateCoupon:
    """
    A class to validate incoming coupons based on the business line and active status.

    """

    def validate_coupons(self, code: List[str],
                         business_line: List[str],
                         is_active: List[bool]) -> List[str]:
        """
        The method to validate the coupons.
        :param code: incoming list of coupons for checking
        :param businessLine: list of business lines for each coupon
        :param isActive: the status of each coupon
        :return: checking the result, true if valid.
        """

        valid_business_lines = ["electronics", "grocery", "pharmacy", "restaurant"]
        pattern = re.compile(r'[^a-zA-Z0-9_]+')
        templist = []

        # aggregates elements from multiple iterables
        for c, b, a in zip(code, business_line, is_active):
            if c == "" or b not in valid_business_lines or a is False:
                continue

            if pattern.search(c):
                continue
            # add the tuple
            templist.append( (c, b))

        # let's sort it by business line, then code itself

        result = [sentry[0] for sentry in sorted(templist, key=lambda entry:(entry[1], entry[1]))]

        return result
