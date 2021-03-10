package algorithmTest.reverseArray;

/**
 * @author JIAJUN KOU
 *
 * 对指定的数组元素进行反转
 */
public class ReverseArray {

    public static int[] reverse(int[] arr){
        int n=arr.length;
        int temp;
        for (int start = 0,end=n-1; start <=end ; start++,end--) {
            temp=arr[start];
            arr[start]=arr[end];
            arr[end]=temp;
        }
        return arr;
    }


    public static int[] reverse2(int[] a){
        //用一个结果数组存
        int[] result=new int[a.length];

        for (int i = 0,j=result.length-1; i <a.length ; i++,j--) {
            result[j]=a[i];
        }
        return result;

    }


    public static void main(String[] args) {
        int[] a={1,2,3,4,5};
//        int[] reverse = reverse(a);
//        for (int i : reverse) {
//            System.out.println(i);
//        }

        int[] ints = reverse2(a);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }

    }


}
