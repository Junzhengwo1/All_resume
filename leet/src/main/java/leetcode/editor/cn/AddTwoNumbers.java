//给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。 
//
// 请你将两个数相加，并以相同形式返回一个表示和的链表。 
//
// 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。 
//
// 
//
// 示例 1： 
//
// 
//输入：l1 = [2,4,3], l2 = [5,6,4]
//输出：[7,0,8]
//解释：342 + 465 = 807.
// 
//
// 示例 2： 
//
// 
//输入：l1 = [0], l2 = [0]
//输出：[0]
// 
//
// 示例 3： 
//
// 
//输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
//输出：[8,9,9,9,0,0,0,1]
// 
//
// 
//
// 提示： 
//
// 
// 每个链表中的节点数在范围 [1, 100] 内 
// 0 <= Node.val <= 9 
// 题目数据保证列表表示的数字不含前导零 
// 
// Related Topics 递归 链表 数学 
// 👍 7180 👎 0

package leetcode.editor.cn;
//Java：两数相加
public class AddTwoNumbers{
    public static void main(String[] args) {
        Solution solution = new Solution();
        // TO TEST
        ListNode listNode = new ListNode();
        listNode.add(2);
        listNode.add(4);
        listNode.add(3);
        ListNode listNode1 = new ListNode();
        listNode1.add(5);
        listNode1.add(6);
        listNode1.add(4);
        ListNode listNode2 = solution.addTwoNumbers(listNode, listNode1);

        listNode.list(listNode2);
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public static ListNode head;
        public static ListNode tail;
        public void add(int n){
            head = new ListNode(0);
            ListNode temp = head;
            for (int i = 1; i < n+1; i++) {
                temp.next = new ListNode(i);
                temp = temp.next;
            }
            tail = temp;
        }

        /**
         * 打印链表
         * @param node
         */
        public void list(ListNode node){
            ListNode temp = node;
            while(temp!=null){
                System.out.println(temp.val);
                temp = temp.next;
            }
        }

    }



    //输入：l1 = [2,4,3], l2 = [5,6,4]
    //输出：[7,0,8]
    static class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            int total = 0;
            int next  = 0;
            ListNode result = new ListNode();
            ListNode current = result;
            while(l1 != null && l2!= null){
                total= l1.val+l2.val+next;
                current.next=new ListNode(total%10);
                next = total/10;
                l1 =l1.next;
                l2 =l2.next;
                current = current.next;
            }
            while (l1 != null){
                total = l1.val+next;
                current.next=new ListNode(total%10);
                next = total/10;
                l1 = l1.next;
                current = current.next;
            }
            while (l2 != null){
                total = l2.val+next;
                current.next=new ListNode(total%10);
                next = total/10;
                l2 = l2.next;
                current = current.next;
            }
            if (next != 0){
                current.next=new ListNode(next);
            }
            return result.next;
        }
    }


}