package com.kou.base.dataStructure.queue;

import java.util.Iterator;

/**
 * @author JIAJUN KOU
 *
 * 队列实现（采用链表实现）
 *
 */
public class QueueSelf<T> implements Iterable<T> {

    //记录首节点
    private Node head;
    //记录尾节点
    private Node last;
    //记录队列中元素的个数
    private int N;

    public QueueSelf(){
        this.head=new Node(null,null);
        this.last=null;
        this.N=0;
    }


    /**
     * 节点类
     */
    private class Node{
        public T val;
        public Node next;

        public Node(T val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    //判断是否为空
    public boolean isEmpty(){
        return N==0;
    }

    //返回队列中元素的个数
    public int size(){

        return N;
    }

    //向队列中插入元素t
    public void enqueue(T t) {
        if(last==null){
            last=new Node(t,null);
            head.next=last;
        }else {
            Node oldLast=last;
            last=new Node(t,null);
            oldLast.next=last;
        }
        N++;
    }

    //从队列中拿出一个元素
    public T dequeue(){
        if(isEmpty()){
            return null;
        }
        Node oldFirst=head.next;
        head.next=oldFirst.next;
        N--;
        if(isEmpty()){
            last=null;
        }
        return oldFirst.val;
    }

    @Override
    public Iterator<T> iterator() {
        return new QIterator();
    }

    private class QIterator implements Iterator{

        private Node n;

        public QIterator() {
            this.n = head;
        }

        @Override
        public boolean hasNext() {
            return n.next!=null;
        }

        @Override
        public Object next() {
            n=n.next;
            return n.val;
        }
    }

}
