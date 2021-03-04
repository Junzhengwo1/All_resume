package printTriangle;

/**
 * @author JIAJUN KOU
 */
public class PrintTriangle {
    /**
     * 打印各种三角形
     */
    public static void main(String[] args) {
        //int[] nums=new int[6];
        //threeIncreasing(nums);
        //threeDimin(nums);
        a();//打印正等腰三角
//        b();//打印倒腰三角
//        d();//打印直边靠右正直角三角
//        e();//打印直边靠左倒直角三角
//        g();//打印底边靠左钝角角三角
//        h();//打印底边靠右钝角角三角
    }
    public static void threeIncreasing(int[] nums){
        for(int i=1;i<nums.length;i++){
            for(int j=0;j<i;j++){
                System.out.print("*"+"\t");
            }
            System.out.println();
        }
    }
    public static void threeDimin(int[] nums){
        for(int i=nums.length;i>=1;i--){
            for(int j=0;j<i;j++){
                System.out.print("*");
            }
            System.out.println();
        }
    }
    public static void a(){
        System.out.println("打印正等腰三角");
        int i ,j;
        for(i=1;i<=5;i++){
            for(j=5;j>i;j--){
                System.out.print(" ");
            }
            for(j=0;j<i*2-1;j++){
                System.out.print("*");
            }
            System.out.println();
        }
    }
    public static void b(){
        System.out.println("打印倒腰三角");
        int i,j;
        int[] nums=new int[5];
        int nl=nums.length;
        for(i=1;i<=nl;i++){
            for(j=0;j<i;j++){
                System.out.print(" ");
            }
            for(j=nl*2-1;j>=i*2-1;j--){
                System.out.print("*");
            }
            System.out.println();
        }
    }
    public static void d(){
        System.out.println("打印直边靠右正直角三角");
        int i ,j;
        for(i=1;i<=5;i++){
            for(j=5;j>i;j--){
                System.out.print(" ");
            }
            for(j=0;j<i;j++){
                System.out.print("*");
            }
            System.out.println();
        }
    }
    public static void e(){
        System.out.println("打印直边靠左倒直角三角");
        int i ,j;
        for(i=1;i<=5;i++){
            for(j=5;j>=i;j--){
                System.out.print("*");
            }
            System.out.println();
        }
    }
    public static void g(){
        System.out.println("打印底边靠左钝角角三角");
        int i ,j ;
        for(i=1;i<=5;i++){
            for(j=0;j<i;j++){
                System.out.print("*");
            }
            System.out.println();
        }
        for(i=1;i<5;i++){
            for(j=5;j>i;j--){
                System.out.print("*");
            }
            System.out.println();
        }
    }

    public static void h(){
        System.out.print("打印底边靠右钝角角三角");
        int i,j;
        for(i=0;i<=5;i++){
            for(j=5;j>i;j--){
                System.out.print(" ");
            }
            for(j=0;j<i;j++){
                System.out.print("*");
            }
            System.out.println();
        }
        for(i=1;i<5;i++){
            for(j=0;j<i;j++){
                System.out.print(" ");
            }
            for(j=5;j>i;j--){
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
