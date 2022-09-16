package com.kou.base.algorithmTest.reverseLinked;

/**
 * @author JIAJUN KOU
 *
 * 链表反转 笔试题
 */
public class ReverseLinked {

    public static class ListNode{
        int val;
        ListNode next;

        ListNode(int x){
            val=x;
        }
        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    '}';
        }
    }

    public static ListNode head;
    public static ListNode tail;

    /**
     * 相当于就是把一个链表创建了
     * @param n
     */
    public static void add(int n){
        head = new ListNode(0);
        ListNode temp = head;
        for (int i = 1; i < n+1; i++) {
            temp.next = new ListNode(i);
            temp = temp.next;
        }
        tail = temp;
    }

    public ListNode reverseList(){
        return reverseList(head);
    }

    public ListNode reverseList(ListNode head){

        //定义两个节点变量
        //1.定义前指针节点
        ListNode prev=null;
        //2.定义一个当前节点
        ListNode curr=head;
        while (curr!=null){
            //开辟一个临时节点暂存当前节点的下一个节点
            ListNode nextTemp=curr.next;
            //当前节点指向它前面的节点
            curr.next=prev;
            prev=curr;
            curr=nextTemp;
        }
        return prev;
    }

    /**
     * 打印链表
     * @param node
     */
    public static void list(ListNode node){
        ListNode temp = node;
        while(temp!=null){
            System.out.println(temp);
            temp = temp.next;
        }
    }

    public static void main(String[] args) {
        ReverseLinked r = new ReverseLinked();

        ReverseLinked.add(5);
        ReverseLinked.list(ReverseLinked.head);
        System.out.println("--------------------");
        r.reverseList();
        ReverseLinked.list(ReverseLinked.tail);
    }
}
