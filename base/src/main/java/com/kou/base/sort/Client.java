package sort;

import java.util.Arrays;

/**
 * @author JIAJUN KOU
 */
public class Client {
    public static void main(String[] args) {
        int[] a={1,100,3,4};
        int[] b={120,23,56,25,1,2};
//        int[] ints = AllSort.bubbleSort(new int[]{1,3,4,650});
//        for (int anInt : ints) {
//            System.out.println(anInt);
//        }
//
//        int[] ints1 = AllSort.selectSort(a);
//        for (int i : ints1) {
//            System.out.println(i);
//        }
//
//        AllSort.insertSort(a);
        //AllSort.shellSort(b);
        AllSort.quickSort(b);
        System.out.println(Arrays.toString(b));

    }
}
