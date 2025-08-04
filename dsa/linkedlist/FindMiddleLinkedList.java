package dsa.linkedlist;

import java.util.List;
import java.util.Objects;

/*

876
https://leetcode.com/problems/middle-of-the-linked-list/description/

This shows how to use fast and slow pointers to get the middle of a linked list.
 */
public class FindMiddleLinkedList {

    private static class ListNode {
        private ListNode next;
        private int val;

        ListNode() {}
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

    } // class - ListNode

    private ListNode middleNode(ListNode head) {

        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

        }

        return slow;
    }

    private ListNode detectCycle(ListNode head) {
        /*

        142 - Floyd’s Cycle Detection Algorithm (Tortoise and Hare)
        https://leetcode.com/problems/linked-list-cycle-ii/description/

         */

        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                // cycle found
                slow = head;
                while (fast != slow) {
                    slow = slow.next;
                    fast = fast.next;
                }

                return slow;
            }

        }

        // no cycle
        return null;

    }

    public void rotateRight(ListNode head, int k) {

        /*
        https://leetcode.com/problems/rotate-list

        [1] [2] [3] [4]
          when head ([1]) move 3 steps, the tail([4]) also moves 3 steps. (->)
          it is similar to moving tail left by 3 steps  (<-)

          so, to smartly locate the new head, it will be nodeCount - stepRotate

         */
        printNodes(head);

        ListNode tail = head;
        int nodeCount = 1;

        // count the number of nodes
        while (tail.next != null) {
            tail = tail.next;
            nodeCount++;
        }

        // cicle the list
        tail.next = head;

        // scale k into the scale of nodeCount
        int stepsRotate = k % nodeCount;

        // find the node that will be the new head
        int stepsMoved = nodeCount - stepsRotate;

        while (stepsMoved > 0) {
            tail = head;
            head = head.next;
            stepsMoved--;
        }

        // break the cycle
        tail.next = null;

        printNodes(head);
    }

    private ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        /*
        160
        https://leetcode.com/problems/intersection-of-two-linked-lists/description/

        If an intersection node is found, both headA and headB transverse the same distance.

        lenA + lenB + X == lenB + lenA + X
        => implying that X can be any value from positive to negative

        1 -> 2 -> 3  *   ( either A or B takes 3 steps to node(3) )

         4 -> 5  ^

        1 -> 2 -> 10 -> 3  *   A reach node(3) at 4, 7   : B reach node(3) at 3, 7
         4 -> 5  ^

        1 -> 2 -> 10 -> 11 -> 3  *   A reach node(3) at 5, 8   : B reach node(3) at 3, 8
         4 -> 5  ^
                   if interNode at tail, steps moved = lenA + lenB
         */

        ListNode nodeA = headA;
        ListNode nodeB = headB;

        while (nodeA != nodeB) {
            nodeA = ( nodeA == null ) ? headB : nodeA.next;
            nodeB = ( nodeB == null ) ? headA : nodeB.next;
        }

        int value = nodeA == null ? -1 : nodeA.val;

        System.out.printf("Intersection node=%s", value);
        return nodeA;
    }

    private void removeNthNodeFromEnd(ListNode head, int n) {

        /*
        https://leetcode.com/problems/remove-nth-node-from-end-of-list/description/

        Idea

        1 -> 2 -> 3 -> 4 -> 5,    n = 2
                       D
                 fast
                 slow              <= ideal position

        1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7,    n = 4
                       D
                            fast
                 slow              <= ideal position

            The list starts from one rather than zero
             lenDFromStart (previousNode of D) + lenDFromEnd = len(List)
               => lenDFromStart (previousNode of D) = len(List) - lenDFromEnd
                  => slowStep = Len(List) - fastStep

         */

        ListNode fast = head;
        ListNode slow = head;

        // fast forward head to n steps
        for (int i=0; i < n; i++) {
            fast = fast.next;
            if (fast == null) {
                break;
            }
        }

        if (fast == null) {
            System.out.printf("WARN: Step n=%s longer than the length of the list %n", n);
            return;
        }
        // move both fast and slow together and stop when fast reaches the end
        // we need to stop at a node before the deleted node, so the condition is fast.next instead of fast != null
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        ListNode deletedNode = slow.next;
        slow.next = deletedNode == null ? null : deletedNode.next;

        printNodes(head);
    }

    private void swapTwoAdjacentNodes(ListNode head) {

        /*

        24: swap nodes in pairs
        https://leetcode.com/problems/swap-nodes-in-pairs/description/
            i.e. swap (1-2), and then (3-4)

           1 -> 2 -> 3 -> 4    =>   2 -> 1 -> 4 -> 3
     Prev  C

           Prev(1)->(2)
                    C(2)->(3)

               f.next = s.next
               c.next = s
               s.next = f


           dummy       current         current(next)    current(nextnext)   firstNode    secondNode
           null        head(1)         2                3                   2             3


         */

        if (head == null || head.next == null) {
            return;
        }

        ListNode prevNode = new ListNode(), nextNode, currentNode;
        ListNode firstNode, secondNode;

        prevNode.next = head;
        currentNode = prevNode;

        while (currentNode.next != null && currentNode.next.next != null) {
            /*
              1 -> 2 -> 3 -> 4    =>  2 -> 1 -> 4 -> 3
           C
              F    S
         2nd iter
              C
                        F    S

              travel 1 and 2
             */
            firstNode = currentNode.next;
            secondNode = currentNode.next.next;

            firstNode.next = secondNode.next;      // update N(1).next to N(3)
            currentNode.next = secondNode;         // update C(0).next to N(2)
            secondNode.next = firstNode;           // update N(2).next to N(1)

            currentNode = firstNode;          // C(0)->N(2)->N(1)->N(3)->N(4) : currentNode = N(1)
            // So, C moves from 0 to 1 ( two steps )
            // 2nd iteration
            // C(0)->N(2)->N(1)->N(4)->N(3)
        }

        head = prevNode.next;
        printNodes(prevNode.next);

    }

    private void deleteDuplicatesSortedList(ListNode head) {

        /*
            case 1:  1 - 2 - 2 - 3 => 1 - 3
            case 2:  2 - 2 - 2 - 3 => 3
         */
        ListNode prevNode = new ListNode(0);
        prevNode.next = head;    // we want to have prevNode one step before currentNode

        ListNode currentNode = head;
        boolean duplicate =false;

        while (currentNode != null) {

            while (currentNode.next != null && currentNode.val == currentNode.next.val) {
                duplicate = true;
                currentNode = currentNode.next;

            }

            if (duplicate) {

                if (head.val == prevNode.next.val) {
                   /* update head if
                       2 - 2 - 2 - 3
                       H ---------> move ot here (3)
                    P.next
                               C->next
                  */

                    head = currentNode.next;
                }

                if (prevNode.val == currentNode.val ) {
                   /* update head if
                       2 - 2 - 2 - 3
                                   H (new-pos)
                    P -------> move to here (2)
                               C
                  */
                    prevNode = currentNode;

                } else {
                   /* update head if
                       1 - 2 - 2 - 3
                       H
                    P.next-------> C.next(3)
                               C
                  */

                    prevNode.next = currentNode.next;
                    prevNode = currentNode;
                }

                duplicate = false;

            } else {
                // duplicate is false
                // move prevNode right before the duplicate
                prevNode = prevNode.next;

            }
            currentNode = currentNode.next;
        }

        printNodes(head);

    }

    private void printNodes(ListNode head) {

        ListNode node = head;

        while (node != null) {
            System.out.printf(" [%s] ", node.val);
            node = node.next;
        }

        System.out.printf("%n");
    }

    public void testMiddleNode() {

        ListNode node4 = new ListNode(4);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);

        ListNode result = middleNode(node1);

    }

    private void testCycle() {

        ListNode node4 = new ListNode(4);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);

        node4.next = node1;

        ListNode cycleNode = detectCycle(node1);
        int cycleNodeValue = cycleNode != null ? cycleNode.val : -1;
        System.out.println("cycleNode=" + cycleNodeValue);

    }

    private void testRotate() {

        ListNode node5 = new ListNode(5);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);

        rotateRight(node1, 1);

    }

    private void testIntersectionNode() {

        ListNode node3 = new ListNode(3);    // intersection
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);

        ListNode node5 = new ListNode(5, node3);   // intersection
        ListNode node4 = new ListNode(4, node5);

        ListNode interNode = getIntersectionNode(node4, node1);

    }

    private void testSwapTwoAdjacentNodes() {

        ListNode node5 = new ListNode(5);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);

        swapTwoAdjacentNodes(node1);

    }

    private void testDeleteDuplicateSortedList() {

        ListNode node4_3 = new ListNode(5);
        ListNode node4_2 = new ListNode(4, node4_3);
        ListNode node4_1 = new ListNode(4, node4_2);

        ListNode node3 = new ListNode(3,  node4_1);
        ListNode node2_2 = new ListNode(2, node3);
        ListNode node2_1 = new ListNode(2, node2_2);
        ListNode node1 = new ListNode(1, node2_1);

        deleteDuplicatesSortedList(node1);

    }

    public static void main(String[] arg) {

        FindMiddleLinkedList fl = new FindMiddleLinkedList();

        // fl.testMiddleNode();
        // fl.testCycle();
        // fl.testRotate();
        // fl.testIntersectionNode();
        // fl.testSwapTwoAdjacentNodes();
        fl.testDeleteDuplicateSortedList();

    }
}
