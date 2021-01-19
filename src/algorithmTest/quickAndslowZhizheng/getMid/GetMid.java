package algorithmTest.quickAndslowZhizheng.getMid;

/**
 * @author JIAJUN KOU
 *
 * 快慢指针 获取中间值
 */
public class GetMid {

    /**
     * 节点类
     */
    public static class Node<T> {
        T val;
        Node next;

        public Node(T val, Node next) {
            this.val = val;
            this.next = next;
        }
    }


    /**
     * 获取链表的中间值；采用了快慢指针思想
     * @param first
     * @return
     */
    public static String getMid(Node<String> first){
        //定义两个指针
        Node<String> fast=first;
        Node<String> slow=first;
        //使用两个指针遍历链表，当快指针指到最后时，慢指针所在位置就是中间节点了
        while (fast!=null&&fast.next!=null){
            fast=fast.next.next;
            slow=slow.next;

        }

        return slow.val;
    }

    public static void main(String[] args) {
        Node<String> first=new Node<>("aa",null);
        Node<String> second=new Node<>("bb",null);
        Node<String> third=new Node<>("cc",null);
        Node<String> fourth=new Node<>("dd",null);
        Node<String> fifth=new Node<>("ee",null);

        first.next=second;
        second.next=third;
        third.next=fourth;
        fourth.next=fifth;
        String mid = getMid(first);
        System.out.println(mid);
    }




}
