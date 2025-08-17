package org.cklautests.dsa.stringoperation;

import java.util.List;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*

https://leetcode.com/problems/coupon-code-validator/

Lessons learnt
 - custom order of priority queue only cover the first element,
    so it is not fully sorted
 - To sort a list, let's use a simple ArrayList instead with a
    custom comparator or a TreeSet (more convenient)
 */
public class ValidateCoupon {

    /**
     * Validate if the incoming coupons are valid based on the given conditions.
     * @param code  - the coupon to be checked
     * @param businessLine - the businessline each coupon belongs to
     * @param isActive - the status of each coupon
     * @return - a list of valid coupons sorted by business lines,
     *          followed by the coupon names.
     */
    public List<String> validateCoupons(String[] code,
                                        String[] businessLine,
                                        boolean[] isActive) {

        List<String> result = new LinkedList<>();


        Comparator<String[]> comp = (o1, o2) -> {
            // 0 - code
            // 1 - business line

            /*
              compareTo( o1, o2)
                 negative => o1 - then -> o2
                 zero => same pos
                 positive => o2 - then - o1
             */

            if (o1[1].compareTo(o2[1]) != 0) {
                return o1[1].compareTo(o2[1]);
            }
            return o1[0].compareTo(o2[0]);
        };

        TreeSet<String[]> treeSet = new TreeSet<>(comp);

        int codelen = code.length;

        Set<String> validateBusinessLines = new HashSet<>() {{
            add("electronics");
            add("grocery");
            add("pharmacy");
            add("restaurant");
        }};

        String regx = "[^a-zA-Z0-9_]+";   // not alphanumeric or underscore

        Pattern p = Pattern.compile(regx);

        for (int i = 0; i < codelen; i++) {
            if (!isActive[i]
                    || !validateBusinessLines.contains(businessLine[i])
                    || code[i].isEmpty()) {
                continue;
            }
            Matcher matcher = p.matcher(code[i]);
            if (matcher.find()) {
                continue;
            }
            // q.add(new String[] {code[i], businessLine[i]});
            System.out.printf("[%s]: code=[%s], bline=[%s] %n ", i, code[i], businessLine[i]);
            // templist.add(new String[] {code[i], businessLine[i]});
            treeSet.add(new String[] {code[i], businessLine[i]});
        } // for - i

        // templist.sort(comp);

        for (String[] entry: treeSet) {
            result.add(entry[0]);
        }

        return result;
    }

}
