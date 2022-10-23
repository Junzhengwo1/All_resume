package com.kou.base.binSearch;

import java.util.Scanner;

/**
 * @author JIAJUN KOU
 *
 * 二分查找数组：
 *
 */
public class BinSearch {

    /**
     * 二分查找;
     * @param a 查找的数组
     */
    public static int binSearch(int [] a){
        //第一个变量，起始位置
        int start = 0;
        //第二个变量，结束位置
        int end = a.length-1;
        //第三个变量，待查找数组的中间的位置
        int mid = 0;
        //输入待查找的数字
        int param = new Scanner(System.in).nextInt();
        //保存找到的数字的下标
        //表示待查找的数不存在于数组中
        int index = -1;
        while (start <= end){
            //找到当前待查询数组的中间位置
            mid = (start+end)/2;
            if(a[mid] == param){
                index = mid;
                break;
            }else if (a[mid] < param){
                start = mid+1;
            }else if(a[mid] > param){
                end = mid-1;
            }
        }
        return index;
    }



    public static void main(String[] args) {
        //初始化数组
        int[] a = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        int i = BinSearch.binSearch(a);
        System.out.println(i);
    }
}
