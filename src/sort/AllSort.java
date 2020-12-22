package sort;

import java.util.Arrays;

/**
 * @author JIAJUN KOU
 */
public class AllSort {
    /**
     * 冒泡排序
     * @param b 数组
     * @return
     */
    public static int[] BubbleSort ( int[] b ){
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (b[j] > b[i]) {
                    //中间变量用来交换
                    int tmp = b[j];
                    b[j] = b[i];
                    b[i] = tmp;
                }
            }
        }
        return b;
    }

    /**
     * 直接选择排序
     * @param a 需要排序的数组
     * @param n
     * @return 一个整数的数组
     */
    public static int[] SelectSort(int[]a, int n) {
        int i, j, k;
        int tmp;
        for (i = 0; i < n - 1; i++) {
            k = i;
            for (j = i + 1; j < n; j++) {
                if (a[j] < a[k]) {
                    k = j;
                }
            }
            if (k!= i) {
                tmp = a[i];
                a[i] = a[k];
                a[k] = tmp;
            }
        }
        return a;
    }

    /**
     * 插入排序
     */
    public static void insertSort(int[] array) {
        int i,j,temp;
        for(i=1;i<array.length;i++) {
            temp=array[i];
            for(j=i-1;j>=0;j--) {
                if(temp>array[j]) {
                    break;
                }else {
                    array[j+1]=array[j];
                }
            }
            array[j+1]=temp;
        }
        System.out.println(Arrays.toString(array));
    }











    public static void main(String[] args) {
        int[] a={1,100,3,4};
        int[] ints = AllSort.BubbleSort(new int[]{1,3,4,650});
        for (int anInt : ints) {
          System.out.println(anInt);
        }

        AllSort.insertSort(a);

    }

}
