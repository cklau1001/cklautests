# Background
Below subdirectories contains a list of code for different Leetcode problems grouped by their nature. 
Most of them are Java, with few python codes. Comments are provided to explain some tricky approaches.
Some programs may contain more than one solutions. The purpose is to evaluate different approaches and provide some 
lessons learnt.

# Basic concept
The following concept are helpful to work with the problems.

## String operation
- For any string with length n, the max number of substring is n(n+1) / 2.
```requirements
string : A-B-C
substring         length             sub-total 
  A-B-C              3                   1
  A-B, B-C           2                   2
  A, B, C            1                   3
  
 total = 1 + 2 + 3 ( arithmetic progression )
 
sum of an arithmetic progression = (2a + (n-1)d ) * n/2, where a is the starting value and d is the distance between 2 items
if a = 1 and d = 1
  sum = n/2 * (n+1) = n(n+1)/2
```

## Linked list
- If both a current and previous pointers are needed, set the previous pointer as a dummy node with prev->next = head at the very beginning.
In this case, it can follow current pointer in every iteration.
- To identify cycle or locate the middle of a list, adopt the fast and slow pointers trick such that fast moves two steps ahead of slow pointers.
