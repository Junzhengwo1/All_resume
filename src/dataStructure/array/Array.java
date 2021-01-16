package dataStructure.array;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author JIAJUN KOU
 */
public class Array {

    public static void main(String[] args) {
        int[] a={1,2,3,4};
        int[] arr= new int[] {20,68,34,22,34};
        int[] b=new int[5];
        //实现删除指定的元素
        System.out.println(Arrays.toString(arr));
        System.out.println("请输入要删除第几个元素：");
        Scanner sc =new Scanner(System.in);
        int n = sc.nextInt();
        sc.close();
        //把最后一个元素替代指定的元素
        arr[n-1] = arr[arr.length-1];

        //数组缩容
        arr = Arrays.copyOf(arr, arr.length-1);
        System.out.println(Arrays.toString(arr));

        for (int i : a) {
            System.out.println(i);
        }
        System.out.println(a[0]);
    }



}
