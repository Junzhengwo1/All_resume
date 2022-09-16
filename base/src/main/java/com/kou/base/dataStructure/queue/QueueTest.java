package com.kou.base.dataStructure.queue;

import com.kou.base.dataStructure.queue.QueueSelf;

/**
 * @author JIAJUN KOU
 */
public class QueueTest {
    public static void main(String[] args) {

        QueueSelf<String> queue=new QueueSelf<>();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");

        for (String s : queue) {
            System.out.println(s);
        }

        System.out.println("-----------");
        String dequeue = queue.dequeue();
        System.out.println("出队列的元素"+dequeue);
        System.out.println("剩余队列的个数"+queue.size());

    }
}
