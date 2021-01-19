package stack;


/**
 * @author JIAJUN KOU
 */
public class StackTest {
    public static void main(String[] args) {

        LinkedStack<String> stack=new LinkedStack<>();

        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");

        for (String s : stack) {
            System.out.println(s);
        }

        System.out.println("--------------------");

        String pop = stack.pop();
        System.out.println("弹出的节点为"+pop);
        System.out.println("栈中剩余的元素个数为"+stack.size());


    }
}
