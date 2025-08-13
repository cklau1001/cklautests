package org.cklautests.dsa.dp;

import java.util.*;

/*

46 - permutation
https://leetcode.com/problems/permutations/description/

47 - permutation 2
https://leetcode.com/problems/permutations-ii/description/

input [0,1,2], size = 3

[0]
   [0,1]
       [0,1,2]
   [0,2]
       [0,2,1]
[1]
   [1,0]
       [1,0,2]
   [1,2]
       [1,2,0]
[2]
   [2,0]
       [2,0,1]
   [2,1]
       [2,1,0]

 */
public class Permutation {

    public List<List<Integer>> permutateByRec(int[] nums) {

        /*
          leverage a visited array to mark which entry has been used
          reset the visited entry and remove the lastly added number so that it can be picked up again in different order
          in next iteration.
         */
        boolean[] visited = new boolean[nums.length];
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();

        DFS(nums, 0, visited, path, result);

        return result;
    }

    public void DFS(int[] nums, int pos, boolean[] visited, List<Integer> path, List<List<Integer>> result) {

        if (pos == nums.length) {
            result.add(new ArrayList<>(path));
        }

        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            path.add(nums[i]);
            DFS(nums, pos + 1, visited, path, result);
            path.removeLast();  // remove last added number for next iteration
            visited[i] = false;   // reset last visited number for next iteration to pick up in different order
        }

    }

    public List<List<Integer>> permutateBySwap(int[] nums) {
        /*
           strictly speaking, in each iteration, two paths are taken, either keep the same order or swap the order
           leverage this feature to compute all permutation
         */


        List<Integer> path = new ArrayList<>();    // need to build a mutable list, toList() can only return an immutable list
        for (int n: nums) {
            path.add(n);
        }

        List<List<Integer>> result = new ArrayList<>();
        DFSswap(0, path, result);

        return result;

    }

    public void DFSswap(int pos, List<Integer> path, List<List<Integer>> result) {

        /*
            [0,1,2]

            i=0
               swap(0,0) => no change at 1st position
                   i=1
                     swap(1,1) => no change at 2nd position
                        i=2  ( end of array )
                          no swap   => [0,1,2]
                     swap(1,2) => second postion = 2
                        i=2
                           [0,2,1]
               swap(0,1) => first postion = 1

               swap(0,2) => first postion = 2
         */
        if (pos == path.size()) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i=pos; i < path.size(); i++) {
            Collections.swap(path, pos, i);
            DFSswap(pos+1, path, result);
            Collections.swap(path, pos, i);

        }
    }

    public List<List<Integer>> permuteUnique47(int[] nums) {
        /*

        47
        https://leetcode.com/problems/permutations-ii/description/

        [0,1,1]
          [0]
            [0,1]
              [0,1,1]
          [1]
            [1,0]
                [1,0,1]
                [1,1,0]
          [1]   - no need to swap with 0 again - swap(0,2)

         */
        Arrays.sort(nums);
        List<Integer> path = new ArrayList<>();    // need to build a mutable list, toList() can only return an immutable list
        List<Integer> pathMap = new ArrayList<>();
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int n: nums) {
            path.add(n);
            freqMap.put(n, freqMap.getOrDefault(n, 0) + 1);
        }

        boolean[] visited = new boolean[nums.length];
        Set<List<Integer>> set = new HashSet<>();
        List<List<Integer>> result = new ArrayList<>();
        DFSUniqueSwap(0, path, result, set);
        // DFSUniqueSwap2(nums.length, freqMap, pathMap, result);
        return result;

    }

    public void DFSUniqueSwap(int pos, List<Integer> path, List<List<Integer>> result, Set<List<Integer>> set) {

        /*
           Not an efficient approach as duplicate computation is needed.

            [0,1,2]

            i=0
               swap(0,0) => no change at 1st position
                   i=1
                     swap(1,1) => no change at 2nd position
                        i=2  ( end of array )
                          no swap   => [0,1,2]
                     swap(1,2) => second postion = 2
                        i=2
                           [0,2,1]
               swap(0,1) => first postion = 1

               swap(0,2) => first postion = 2
         */
        if (pos == path.size()) {
            if (!set.contains(path)) {
                result.add(new ArrayList<>(path));
                set.add(path);
                return;
            }
        }

        for (int i=pos; i < path.size(); i++) {
            Collections.swap(path, pos, i);
            DFSUniqueSwap(pos+1, path, result, set);
            Collections.swap(path, pos, i);

        }
    }

    public void DFSUniqueSwap2(int numlength, Map<Integer, Integer> freqMap, List<Integer> path, List<List<Integer>> result) {

        /*
        Use a frequency map to capture frequency of each result, which can also avoid duplicate

          [0,1,1,2]    => freqMap = { 0:1 / 1:2 / 2:1 }

          [0]
             [0,1,1,2]

         */
        if (path.size() == numlength ) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int key: freqMap.keySet()) {
            int count = freqMap.get(key);
            if (count > 0) {
                path.add(key);
                count--;
                freqMap.put(key, count);
                DFSUniqueSwap2(numlength, freqMap, path, result);
                path.removeLast();
                count++;
                freqMap.put(key, count);
            }
        }

    }

    public static void main(String[] args) {

        int[] input = new int[]{1,1,2};
        List<List<Integer>> result;

        Permutation p1 = new Permutation();
        // result = p1.permutateByRec(input);
        // result = p1.permutateBySwap(input);
        result = p1.permuteUnique47(input);

        result.forEach(System.out::println);

    }
}
