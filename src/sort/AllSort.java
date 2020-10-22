package sort;

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













    public static void main(String[] args) {
        //int[] a={1,2,3,4};
        int[] ints = AllSort.BubbleSort(new int[]{1,3,4,650});
        for (int anInt : ints) {
          System.out.println(anInt);
        }


    }

}
