package dsa.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*

78 - subset
https://leetcode.com/problems/subsets/description/

90 - subset with duplicate
https://leetcode.com/problems/subsets-ii/description/


39 - combination sum
https://leetcode.com/problems/combination-sum/description/

40 - combination sum 2
https://leetcode.com/problems/combination-sum-ii/description/

131 - palindrome partitioning
https://leetcode.com/problems/palindrome-partitioning/description/
 */
public class Subset {

    public List<List<Integer>> subsetsByRec(int[] nums) {
        /*

        78
        https://leetcode.com/problems/subsets/description/

        nums = [1,2,3]
          every iteration is just a take-it or skip-it
          iterating from nums[0] -> nums[1] -> .... nums[n-1]

          [1]                         # take-it
             [1,2]
                [1,2,3]
                [1,2]
             [1]
                [1,3]
                [1]
          [ ]                        # skip it
            [2]
               [2,3]
               [2]
            [ ]
               [3]
               [ ]

         */

        List<Integer> path = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        DFS(nums, 0, path, result);

        return result;
    }

    public void DFS(int[] nums, int pos, List<Integer> path, List<List<Integer>> result) {
        /*
           nums: [1,2]
                                            path
           [1]                              [1]
              [1,2]                           [1,2]
              [1]                             [1]
           []                               []
             [2]                              [2]
             [ ]                              [ ]
         */
        if (pos == nums.length) {
            /*
               we only store the result when transversing to the last element, which is backtracking.
             */
            result.add(new ArrayList<>(path));   // to workaround call-by-reference, need to create a new copy (defensive copy)
            return;
        }

        // take-it
        path.addLast(nums[pos]);           // this is a LIFO stack operation
        DFS(nums, pos+1, path, result);

        // skip-it
        /* this is a LIFO stack operation, remove() will remove at pos, not the target value
           better to use removeLast()
         */
        path.removeLast();
        DFS(nums, pos+1, path, result);

    }

    public List<List<Integer>> subsetsByBit(int[] nums) {

        /*
          a bit manipulation is a smart way to construct all combination in a brute force manner.
          nums = [1, 2, 3]
           for any matched entry, e.g. [1,3] =>  0x1 0 1

              How to translate a bitmap into target entry of nums by shifting 1 to right
              ----------------------------------------------------------------------------
              i=5 (0x101) and j=0, 0x000) => 1   => shift 0 times of 1 to right => num[0] = 1
              i=5 (0x101) and j=2, 0x200) => 3   => shift 2 times of 1 to right => num[2] = 3
         */
        List<List<Integer>> result = new ArrayList<>();

        for (int i=0; i < (1 << nums.length); i++) {
            List<Integer> path = new ArrayList<>();
            for (int j=0; j < nums.length; j++) {
                int shiftR = i >> j;
                if ((shiftR & 1) == 1) {
                    path.add(nums[j]);
                }
            }
            result.add(new ArrayList<>(path));
        }

        return result;
    }

    public List<List<Integer>> subsets2WithDup(int[] nums) {
        /*

        90 - nums can have duplicate
        https://leetcode.com/problems/subsets-ii/description/

       we only want to include [], [2,2], [2,2,2],.... once from bitwise perspective, it will be
       accepted -> 0x0000, 0x1000, 0x1100, 0x1110, 0x1111
       not accepted -> 0x0100, 0x0010, 0x0001, etc

       the add-it side can help us include the accepted combination. The skip-it side needs to move to next non-repeating characters.

[1,2,2,3]

     1      2    2       3
     [1]
         [1,2]
             [1,2,2]
                 [1,2,2,3]
                 [1,2,2]
             p=[1,2]
                 [1,2,3]
                 [1,2]
        p=[1]
               [1,3]
               [1]
     [ ]
        [2]
           [2,2]
               [2,2,3]
               [2,2]
           p=[2]
               [2,3]
               [2]
        p=[ ]
              [3]
              [ ]

# skip-it

# for any duplicate
   do add-it
      path.add(num[pos])
      DFS(num, pos+1, path, result)
   for skip-it, jump to next non-duplicate
      path.removelast()
      DFS(num, pos+k, path, result)


         */


        // sort the array
        Arrays.sort(nums);
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        DFS2(nums, 0, path, result);

        return result;
    }

    public void DFS2(int[] nums, int pos, List<Integer> path, List<List<Integer>> result) {

        if (pos == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        // add-it
        path.addLast(nums[pos]);
        DFS2(nums, pos + 1, path, result);

        // skip-it
        path.removeLast();

        // find next pos where nums[pos] != nums[k]
        int k = pos + 1;

        while (k < nums.length && nums[pos] == nums[k]) {
            k++;
        }

        DFS2(nums, k, path, result);

    }

    public static void main(String[] args) {

        int[] input = new int[] {1,2,2};
        List<List<Integer>> result = null;

        Subset st = new Subset();
        // result = st.subsetsByRec(input);
        // result = st.subsetsByBit(input);
        result = st.subsets2WithDup(input);

        result.forEach(System.out::println);
    }

}
