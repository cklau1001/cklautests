package org.cklautests.dsa.linkedlist;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FindMiddleLinkedListTest {

    final FindMiddleLinkedList fl = new FindMiddleLinkedList();

    @Test
    void testMiddleNode() {
        FindMiddleLinkedList.ListNode node4 = new FindMiddleLinkedList.ListNode(4);
        FindMiddleLinkedList.ListNode node3 = new FindMiddleLinkedList.ListNode(3, node4);
        FindMiddleLinkedList.ListNode node2 = new FindMiddleLinkedList.ListNode(2, node3);
        FindMiddleLinkedList.ListNode node1 = new FindMiddleLinkedList.ListNode(1, node2);

        FindMiddleLinkedList.ListNode result = fl.middleNode(node1);
        // System.out.println(result.getVal());
        assertThat(result.getVal()).as("Test middle node").isEqualTo(3);

        FindMiddleLinkedList.ListNode singleNode = new FindMiddleLinkedList.ListNode(1);
        result = fl.middleNode(singleNode);
        assertThat(result).as("Test middle node with single node").isEqualTo(singleNode);
    }

    @Test
    void testCycle() {

        /*
            case 1 : cycle at node4 to node1
            1 > 2 > 3 > 4 ---> 1
         */
        FindMiddleLinkedList.ListNode node4 = new FindMiddleLinkedList.ListNode(4);
        FindMiddleLinkedList.ListNode node3 = new FindMiddleLinkedList.ListNode(3, node4);
        FindMiddleLinkedList.ListNode node2 = new FindMiddleLinkedList.ListNode(2, node3);
        FindMiddleLinkedList.ListNode node1 = new FindMiddleLinkedList.ListNode(1, node2);

        node4.setNext(node1);

        FindMiddleLinkedList.ListNode cycleNode = fl.detectCycle(node1);
        int cycleNodeValue = cycleNode != null ? cycleNode.getVal() : -1;
        assertThat(cycleNodeValue)
                .as("Test cycle node detection on a cycled list")
                .isEqualTo(1);

        /*
            case 5 : cycle at node 2
            1 > 2 > 3 > 4 ---> 2

         */
        node4.setNext(node2);
        cycleNode = fl.detectCycle(node1);
        cycleNodeValue = cycleNode != null ? cycleNode.getVal() : -1;
        assertThat(cycleNodeValue)
                .as("Test cycle node detection on a cycled list")
                .isEqualTo(2);


        /*
            case 2 : no cycle
            1 > 2 > 3 > 4
         */
        node4.setNext(null);
        cycleNode = fl.detectCycle(node1);
        cycleNodeValue = cycleNode != null ? cycleNode.getVal() : -1;
        assertThat(cycleNodeValue)
                .as("Test cycle node detection on a non-cycled list")
                .isEqualTo(-1);

        // case 3: edge case: singleNode
        FindMiddleLinkedList.ListNode singleNode = new FindMiddleLinkedList.ListNode(1);
        cycleNode = fl.detectCycle(singleNode);
        cycleNodeValue = cycleNode != null ? cycleNode.getVal() : -1;
        assertThat(cycleNodeValue)
                .as("Test cycle node detection on a one-node list")
                .isEqualTo(-1);

        // case 4: no linked list
        cycleNode = fl.detectCycle(null);
        cycleNodeValue = cycleNode != null ? cycleNode.getVal() : -1;
        assertThat(cycleNodeValue)
                .as("Test cycle node detection on a one-node list")
                .isEqualTo(-1);


    }

    @Test
    void testRotate() {

        /*
          1 > 2 > 3 > 4 > 5
            after rotate by 1
          5 > 1 > 2 > 3 > 4
         */
        FindMiddleLinkedList.ListNode node5 = new FindMiddleLinkedList.ListNode(5);
        FindMiddleLinkedList.ListNode node4 = new FindMiddleLinkedList.ListNode(4, node5);
        FindMiddleLinkedList.ListNode node3 = new FindMiddleLinkedList.ListNode(3, node4);
        FindMiddleLinkedList.ListNode node2 = new FindMiddleLinkedList.ListNode(2, node3);
        FindMiddleLinkedList.ListNode node1 = new FindMiddleLinkedList.ListNode(1, node2);

        fl.rotateRight(node1, 1);
        assertThat(node5.getNext()).as("Test rotate, node5 points to node1").isEqualTo(node1);
        assertThat(node4.getNext()).as("Test rotate, node4 is tail").isNull();
    }

    @Test
    void testIntersectionNode() {

        /*
          case 1 : two lists with intersection
         */
        FindMiddleLinkedList.ListNode node3 = new FindMiddleLinkedList.ListNode(3);    // intersection
        FindMiddleLinkedList.ListNode node2 = new FindMiddleLinkedList.ListNode(2, node3);
        FindMiddleLinkedList.ListNode node1 = new FindMiddleLinkedList.ListNode(1, node2);

        FindMiddleLinkedList.ListNode node5 = new FindMiddleLinkedList.ListNode(5, node3);   // intersection
        FindMiddleLinkedList.ListNode node4 = new FindMiddleLinkedList.ListNode(4, node5);

        FindMiddleLinkedList.ListNode interNode = fl.getIntersectionNode(node4, node1);

        assertThat(interNode).as("Test intersecting node with intersection").isEqualTo(node3);

        /*
          case 2 : two lists without any intersection
         */
        node5.setNext(null);
        interNode = fl.getIntersectionNode(node4, node1);

        assertThat(interNode).as("Test intersecting node without intersection").isNull();

    }

    @Test
    void testSwapTwoAdjacentNodes() {

        /*
             case 1 : a normal linked list with odd length
             before: 1 > 2 > 3 > 4 > 5
             after:  2 > 1 > 4 > 3 > 5
         */
        FindMiddleLinkedList.ListNode node5 = new FindMiddleLinkedList.ListNode(5);
        FindMiddleLinkedList.ListNode node4 = new FindMiddleLinkedList.ListNode(4, node5);
        FindMiddleLinkedList.ListNode node3 = new FindMiddleLinkedList.ListNode(3, node4);
        FindMiddleLinkedList.ListNode node2 = new FindMiddleLinkedList.ListNode(2, node3);
        FindMiddleLinkedList.ListNode node1 = new FindMiddleLinkedList.ListNode(1, node2);

        fl.swapTwoAdjacentNodes(node1);
        assertThat(node2.getNext()).as("Test swapping nodes [2]").isEqualTo(node1);
        assertThat(node1.getNext()).as("Test swapping nodes [1]").isEqualTo(node4);
        assertThat(node4.getNext()).as("Test swapping nodes [4]").isEqualTo(node3);
        assertThat(node3.getNext()).as("Test swapping nodes [3]").isEqualTo(node5);
        assertThat(node5.getNext()).as("Test swapping nodes [5]").isNull();

        /*
           case 2: a normal linked list with even length
             before: 6 > 7
             after:  7 > 6

         */
        FindMiddleLinkedList.ListNode node7 = new FindMiddleLinkedList.ListNode(7);
        FindMiddleLinkedList.ListNode node6 = new FindMiddleLinkedList.ListNode(6, node7);
        fl.swapTwoAdjacentNodes(node6);
        assertThat(node7.getNext()).as("Test swapping nodes [7]").isEqualTo(node6);
        assertThat(node6.getNext()).as("Test swapping nodes [6]").isNull();

        // case 3 : a single-node list
        FindMiddleLinkedList.ListNode singleNode = new FindMiddleLinkedList.ListNode(100);
        fl.swapTwoAdjacentNodes(singleNode);
        assertThat(singleNode.getNext()).as("Test swapping nodes - singleNode").isNull();

    }

    @Test
    void testDeleteDuplicateSortedList() {

        /*
          case 1 : duplicate nodes not start from the beginning
          before : 1 > 2 > 2 > 3 > 4 > 4 > 5
          after  : 1 > 3 > 5
         */

        FindMiddleLinkedList.ListNode node5 = new FindMiddleLinkedList.ListNode(5);
        FindMiddleLinkedList.ListNode node4a2 = new FindMiddleLinkedList.ListNode(4, node5);
        FindMiddleLinkedList.ListNode node4a1 = new FindMiddleLinkedList.ListNode(4, node4a2);

        FindMiddleLinkedList.ListNode node3 = new FindMiddleLinkedList.ListNode(3,  node4a1);
        FindMiddleLinkedList.ListNode node2a2 = new FindMiddleLinkedList.ListNode(2, node3);
        FindMiddleLinkedList.ListNode node2a1 = new FindMiddleLinkedList.ListNode(2, node2a2);
        FindMiddleLinkedList.ListNode node1 = new FindMiddleLinkedList.ListNode(1, node2a1);

        fl.deleteDuplicatesSortedList(node1);
        assertThat(fl.size(node1)).as("Test deleting dup node with size").isEqualTo(3);
        assertThat(node1.getNext()).as("Test deleting dup node [1]").isEqualTo(node3);
        assertThat(node3.getNext()).as("Test deleting dup node [3]").isEqualTo(node5);
        assertThat(node5.getNext()).as("Test deleting dup node [5]").isNull();

        /*
          case 2 : duplicate nodes start from the beginning
          before : 8 > 8 > 8 > 9 > 10 > 10 > 11
          after  : 9 > 11
         */

        FindMiddleLinkedList.ListNode node11 = new FindMiddleLinkedList.ListNode(11);
        FindMiddleLinkedList.ListNode node10a2 = new FindMiddleLinkedList.ListNode(10, node11);
        FindMiddleLinkedList.ListNode node10a1 = new FindMiddleLinkedList.ListNode(10, node10a2);
        FindMiddleLinkedList.ListNode node9 = new FindMiddleLinkedList.ListNode(9, node10a1);
        FindMiddleLinkedList.ListNode node8a3 = new FindMiddleLinkedList.ListNode(8, node9);
        FindMiddleLinkedList.ListNode node8a2 = new FindMiddleLinkedList.ListNode(8, node8a3);
        FindMiddleLinkedList.ListNode node8a1 = new FindMiddleLinkedList.ListNode(8, node8a2);

        fl.deleteDuplicatesSortedList(node8a1);
        assertThat(fl.size(node9)).as("[2] Test deleting dup node with size").isEqualTo(2);
        assertThat(node9.getNext()).as("[2] Test deleting dup node [9]").isEqualTo(node11);
        assertThat(node11.getNext()).as("[2] Test deleting dup node [11]").isNull();
    }

    @Test
    void testremoveNthNodeFromEnd() {

        /*
         * case 1 : n smaller than length of list
         *  possible to remove a node
         *  before: 1 > 2 > 3 > 4 > 5, n = 2 ( remove 2nd node from the end, i.e. 4 )
         *  after:  1 > 2 > 3 > 5
         */
        FindMiddleLinkedList.ListNode node5 = new FindMiddleLinkedList.ListNode(5);
        FindMiddleLinkedList.ListNode node4 = new FindMiddleLinkedList.ListNode(4, node5);
        FindMiddleLinkedList.ListNode node3 = new FindMiddleLinkedList.ListNode(3, node4);
        FindMiddleLinkedList.ListNode node2 = new FindMiddleLinkedList.ListNode(2, node3);
        FindMiddleLinkedList.ListNode node1 = new FindMiddleLinkedList.ListNode(1, node2);

        fl.removeNthNodeFromEnd(node1, 2);
        assertThat(fl.size(node1)).as("[1]: Test removing Nth node, size").isEqualTo(4);
        assertThat(node1.getNext()).as("[1]: Test removing Nth node, (1)" ).isEqualTo(node2);
        assertThat(node2.getNext()).as("[1]: Test removing Nth node, (2)" ).isEqualTo(node3);
        assertThat(node3.getNext()).as("[1]: Test removing Nth node, (3)" ).isEqualTo(node5);
        assertThat(node5.getNext()).as("[1]: Test removing Nth node, (5)" ).isNull();

        /*
         * case 2 : n larger than length of list
         *  no further action needed
         *  before: 6 -> 7, but n = 10
         *  after:  6 -> 7
         */
        FindMiddleLinkedList.ListNode node7 = new FindMiddleLinkedList.ListNode(7);
        FindMiddleLinkedList.ListNode node6 = new FindMiddleLinkedList.ListNode(6, node7);
        fl.removeNthNodeFromEnd(node6, 10);
        assertThat(fl.size(node6)).as("[2]: Test removing Nth node, size").isEqualTo(2);
        assertThat(node6.getNext()).as("[2]: Test removing Nth node, (6)" ).isEqualTo(node7);
        assertThat(node7.getNext()).as("[2]: Test removing Nth node, (7)" ).isNull();

        /*
            n is zero

         */
        FindMiddleLinkedList.ListNode node9 = new FindMiddleLinkedList.ListNode(9);
        FindMiddleLinkedList.ListNode node8 = new FindMiddleLinkedList.ListNode(8, node9);
        fl.removeNthNodeFromEnd(node8, 0);
        assertThat(fl.size(node8)).as("[3]: Test removing Nth node, size").isEqualTo(2);
        assertThat(node8.getNext()).as("[3]: Test removing Nth node, (8)" ).isEqualTo(node9);
        assertThat(node9.getNext()).as("[3]: Test removing Nth node, (9)" ).isNull();


    }
}
