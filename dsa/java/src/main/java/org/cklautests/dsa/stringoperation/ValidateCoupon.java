package org.cklautests.dsa.stringoperation;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*

https://leetcode.com/problems/coupon-code-validator/

Lessons learnt
 - custom order of priority queue only cover the first element, so it is not fully sorted
 - To sort a list, let's use a simple ArrayList instead with a custom comparator or a TreeSet (more convenient)
 */
public class ValidateCoupon {

    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {

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
                System.out.printf("  [comp]: bline: o1=%s:%s, o2=%s:%s, result=%s%n", o1[0],o1[1], o2[0], o2[1], o1[1].compareTo(o2[1]) );
                return o1[1].compareTo(o2[1]);
            }
            return o1[0].compareTo(o2[0]);
        };


        // PriorityQueue<String[]> q = new PriorityQueue<>(comp); // not working as it is not fully sorted
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
            if (!isActive[i] || !validateBusinessLines.contains(businessLine[i]) || code[i].isEmpty()) {
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

    public static void main(String[] args) {
/*
        String[] code = new String[] {"GROCERY15","ELECTRONICS_50","DISCOUNT10"};
        String[] busline = new String[] {"grocery","electronics","invalid"};
        boolean[] isActive = new boolean[] {false, true, true};
 */
/*
        String[] code = new String[] {"SAVE20","","PHARMA5","SAVE@20"};
        String[] busline = new String[] {"restaurant","grocery","pharmacy","restaurant"};
        boolean[] isActive = new boolean[] {true, true, true, true};
*/
/*
        String[] code = new String[] {"GROCERY15","ELECTRONICS_50","DISCOUNT10"};
        String[] busline = new String[] {"grocery","electronics","invalid"};
        boolean[] isActive = new boolean[] {false, true, true};
 */
        String[] code = new String[] {"TsCwKhY","qCeVkfb","ZGjX07H"}; // expected: ["qCeVkfb","ZGjX07H","TsCwKhY"]
        String[] busline = new String[] {"restaurant","electronics","pharmacy"};
        boolean[] isActive = new boolean[] {true, true, true};

        ValidateCoupon vc = new ValidateCoupon();
        List<String> result = vc.validateCoupons(code, busline, isActive);

        result.forEach(System.out::println);

        PriorityQueue<Integer> pint = new PriorityQueue<>();

        pint.offer(10);
        pint.offer(5);
        pint.offer(4);
        pint.offer(9);

        pint.forEach(System.out::println); // output is 4-9-5-10, so not fully sorted

        System.out.println("Using TreeSet instead....");
        TreeSet<Integer> pset = new TreeSet<>();
        pset.add(10);
        pset.add(5);
        pset.add(4);
        pset.add(9);

        pset.forEach(System.out::println); // output is 4-5-9-10, so not fully sorted


    }
}
