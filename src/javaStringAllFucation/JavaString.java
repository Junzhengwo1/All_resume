package javaStringAllFucation;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

/**
 * @author JIAJUN KOU
 */
public class JavaString {

    /**
     * 字符串常量池
     * 例题： String s1=new String ("abc")
     *       String s2= new String ("abc")
     * 创建了几个String 对象？
     *    答案；三个。abc是独立的就在常量池中，另外两个new关键字的就一定会有两个。
     *
     * 结论：一个常量池中不会有相同的常量。
     *       使用new关键字的String对象，一定不在常量池中。
     */

    /**
     * 字符串拼接
     *空对象参与拼接的话，会把null四个字母当作字符拼接起来。
     *
     */

    /**
     * String类的intern()方法：
     * 大概意思就是说：获得常量池中与当前字符串内容相同的字符串
     *
     */


     /**
     *contentEquals()内容比较方法 注意：不可以比较null
     */
    public static boolean comString(){
        String s="abcde";
        String a="ABcdE";
        boolean b = s.contentEquals(new StringBuffer("abcde"));
        boolean b1 = a.equalsIgnoreCase(s);
        return b1;
    }

    public static void compareTest(){
        String s="abcde";
        String d="abcke";
        System.out.println(s.compareTo(d));
    }

    public static void main(String[] args) {
        //boolean b = JavaString.comString();
        //System.out.println(b);
        //JavaString.compareTest();

        String s="Make";
        boolean m = s.startsWith("M");
        boolean e = s.endsWith("e");
        System.out.println(m);
        System.out.println(e);

        //字符串长度length()
        String a="";
        boolean b = a.isEmpty();
        System.out.println(b);

        /**
         * 搜索字符串；注意大小写
         */
        String g="kjjkouwangkou";
        boolean j = g.contains("j");
        System.out.println(j);

        int kou = g.indexOf("kou");//返回的第一个字母的下标
        System.out.println(kou);
        int kou1 = g.indexOf("kou", 7);//开始位置开始搜索
        System.out.println(kou1);

        /**
         * 替换子字符串replaceFirst()
         */
        String k="kouw-angkou";
        //把前面这个字符串替换成后面的这个字符串
        String s1 = k.replaceFirst("kou", "king");
        System.out.println(s1);
        //将字符串中对应的字符全部替换掉
        String s2 = k.replaceAll("kou", "A");
        System.out.println(s2);
        //直接替换replace()字符串（还可以是StringBuilder&StringBuffer）
        String replace = k.replace("-", "//");
        System.out.println(replace);

        /**
         *截取字符串
         */
        String k2="kouwangkouking";
        String substring = k2.substring(2);
        System.out.println("----------------------------截取结果-------------------");
        System.out.println(substring);
        //前包后不包
        String substring1 = k2.substring(3, 7);
        System.out.println("前包后不包");
        System.out.println(substring1);
        CharSequence charSequence = k2.subSequence(7, 10);
        System.out.println(charSequence);
        //就是要指定字段的字符
        char c = k2.charAt(3);
        System.out.println(c);

        /**
         * 拆分字符串
         */
        String k3="kou-wang-king";
        String[] split = k3.split("-");//按照指定的符号来拆分字符
        for (String s3 : split) {
            System.out.println(s3);
        }


        /**
         * 拼接字符串
         */
        System.out.println("字符串拼接---------------------------------------------");
        String k4="kou";
        String k5="jia";
        String k6="jun";
        //concat（） 只支持String,一般不推荐这个方法来拼接，推荐使用（"+"）
        String concat = k4.concat(k5);
        System.out.println(concat);
        //以第一个参数为连接符号，后面的字符连接起来。
        String join = String.join("_", "wang","kou","king");
        System.out.println(join);


        /**
         * 转换字符串
         * 大小写转换
         */
        String k8 ="king-kou-king-wang";
        String s3 = k8.toUpperCase();
        System.out.println(s3);
        String s4 = s3.toLowerCase();
        System.out.println(s4);
        //toCharArray（）返回的就是String字符数组的副本
        char[] chars = k8.toCharArray();
        for (char aChar : chars) {
            System.out.println(aChar);
        }
        //把其他类型的数据转换成String 的对象(所有对象都是可以转换成String的)
        Integer f =100;
        Date date = new Date();
        String s5 = String.valueOf(f);
        System.out.println(s5);
        String s6 = String.valueOf(date);
        System.out.println(date);
        System.out.println(s6);
        //字符数组的操作
        System.out.println(String.valueOf(new char[]{'k', 'o', 'u'}));
        //copyValueOf()只能转换字符数组类型的

        //--------------------------------*----------------------------
        /**
         * StringBuilder 与 StringBuffer的区别
         * StringBuilder不保证同步
         * StringBuffer 同步
         */
        StringBuffer buffer = new StringBuffer("kouwangking");
        StringBuffer append = buffer.append(2);
        System.out.println("buffer.append(new char[]{'j','a'}) = " + buffer.append(new char[]{'j', 'a'}));
        System.out.println(append);


    }

    /**
     * 比较字符串的相关内容
     */



}
