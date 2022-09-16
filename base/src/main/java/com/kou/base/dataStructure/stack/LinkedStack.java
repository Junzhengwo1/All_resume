package com.kou.base.dataStructure.stack;

import java.util.Iterator;

/**
 * @author JIAJUN KOU
 *
 * 使用链表实现栈
 */
public class LinkedStack<T> implements Iterable<T>{
    //记录首节点
    private Node head;
    //栈中元素的个数
    private int N;


    /**
     * 节点类
     */
    private class Node{
        public T val;
        public LinkedStack.Node next;

        public Node(T val, LinkedStack.Node next){
            this.val=val;
            this.next=next;
        }
    }


    //空参构造器
    public LinkedStack() {
        //初始化首节点和元素个数
        this.head=new Node(null,null);
        this.N=0;
    }

    //判断当前栈中元素个数是否为0
    public boolean isEmpty(){
        return N==0;
    }

    //获取栈中元素的个数
    public int size(){
        return N;
    }

    //把t元素压入栈中
    public void push(T t){

        //找到首节点指向的第一个节点
        Node oldFirst=head.next;
        //创建新节点
        Node newNode = new Node(t, null);
        //让首节点指向新节点
        head.next=newNode;
        //让新节点指向原来的第一个节点
        newNode.next=oldFirst;
        //元素个数加一
        N++;
    }

    //弹出栈顶元素
    public T pop(){
        //找到首节点指向的第一个节点
        Node oldFirst=head.next;
        if(oldFirst==null){
            return null;
        }
        //让首节点指向原来第一个节点的下一个节点
        head.next=oldFirst.next;
        N--;
        return oldFirst.val;
    }


    @Override
    public Iterator iterator() {
        return new Siterator();
    }

    public class Siterator implements Iterator{

        private Node a;

        public Siterator(){
            this.a=head;
        }

        @Override
        public boolean hasNext() {
            return a.next!=null;
        }

        @Override
        public Object next() {
            a=a.next;
            return a.val;
        }
    }


}
