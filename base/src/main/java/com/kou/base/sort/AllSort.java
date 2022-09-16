package sort;

import java.util.Arrays;

/**
 * @author JIAJUN KOU
 */
public class AllSort {
    /**
     * 冒泡排序
     * 时间复杂度O(n^2)
     * @param b 数组
     * @return
     */
    public static int[] bubbleSort ( int[] b ){
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
     * 选择排序
     * 时间复杂度O(n^2)
     * @param a 需要排序的数组
     * @return 一个整数的数组
     */
    public static int[] selectSort(int[]a) {
        int i, j, k;
        int tmp;
        for (i = 0; i < a.length - 1; i++) {
            k = i;
            for (j = i + 1; j < a.length; j++) {
                //比较最小索引出的值和j索引处的值
                if (a[j] < a[k]) {
                    k = j;
                }
            }
            //交换最小元素所在索引的值和第一个所在索引的值
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
     * 时间复杂度O(n^2)
     * @param array
     */
    public static void insertSort(int[] array) {
        int i,j,temp;
        //直接从第二个元素开始比较
        for(i=1;i<array.length;i++) {
            temp=array[i];
            //将后面的元素与前面的排好序的元素倒叙循环
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

    /**
     * 希尔排序
     * 时间复杂度O()
     * @param arr
     */
    public static void shellSort(int[] arr){
        //根据数组的长度，确定增长量h的初始值
        int h=1;
        while (h<arr.length/2){
            h=2*h+1;
        }
        while (h>=1){
            //排序
            //找到待插入的元素，
            for (int i = h; i <arr.length; i++) {
                int temp;
                //把待插入的元素插入到有序数列中
                for (int j = i; j >=h ; j-=h) {
                    //待插入的元素a[j]与a[j-h]比较
                    if(arr[j-h]>arr[j]){
                        //交换元素
                        temp=arr[j];
                        arr[j]=arr[j-h];
                        arr[j-h]=temp;
                    }else {
                        //待插入元素找到了合适位置
                        break;
                    }
                }
            }
            //减少增长量h的值
            h=h/2;
        }
        System.out.println(Arrays.toString(arr));

    }


    /**
     * 重载
     * @param arr
     */
    public static void quickSort(int[] arr){
        int lo=0;
        int hi=arr.length-1;
        quickSort(arr,lo,hi);
    }

    /**
     * 快速排序
     * 时间复杂度O(nLogN)
     * @param arr
     * @param leftIndex
     * @param rightIndex
     *
     */
    public static void quickSort(int[] arr, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) {
            return;
        }

        int left = leftIndex;
        int right = rightIndex;
        //待排序的第一个元素作为基准值
        int key = arr[left];


        //从左右两边交替扫描，直到left = right
        while (left < right) {
            while (right > left && arr[right] >= key) {
                //从右往左扫描，找到第一个比基准值小的元素
                right--;
            }
            //找到这种元素将arr[right]放入arr[left]中
            arr[left] = arr[right];

            while (left < right && arr[left] <= key) {
                //从左往右扫描，找到第一个比基准值大的元素
                left++;
            }
            //找到这种元素将arr[left]放入arr[right]中
            arr[right] = arr[left];
        }
        //基准值归位
        arr[left] = key;
        //对基准值左边的元素进行递归排序
        quickSort(arr, leftIndex, left - 1);
        //对基准值右边的元素进行递归排序。
        quickSort(arr, right + 1, rightIndex);
    }

}
