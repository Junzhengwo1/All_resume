package com.kou.base.DateTimeAll;


import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

/**
 * @author JIAJUN KOU
 */
public class TimeTest {

    public static void main(String[] args) throws ParseException {

        /**
         * date类的使用一
         * 注意月份应该减一
         */
        Date date = new Date();
        System.out.println(date);
        Date date1 = new Date(2021-1900,5,7);//这里要减去1900年份
        System.out.println(date1);
        //精确到分钟的构造方法
        //以字符串的构造方法
        Date date2 = new Date("2021/01/20");
        System.out.println(date2);
        //date的一些方法，过时了
        System.out.println(date.getYear()+1900);
        System.out.println(date.getDay());//星期六
        System.out.println(date.getTime());//格林尼治时间的毫秒数
        System.out.println(System.currentTimeMillis());
        //用一个long类型的数据作为参数，构造一个date对象
        Date a = new Date(123456);
        System.out.println(a);
        Date a2 = new Date(-131313131);
        System.out.println(a2);
        /**、
         * ----------------------------格式转化
         */
        //格式转换
        System.out.println(date.toString());
        System.out.println(date.toLocaleString());
        //自定义格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年/MM月/dd日 HH:mm:ss");
        String format = sdf.format(date);
        System.out.println(format);

        //自定义的字符串构建成Date类
        Date date3 = new Date("1999/1/9 21:34:54");
        System.out.println(date3);
        String time="2999/1/9 21:34:54";

        //-----------------------------
        /**
         * 此处有大坑一
         * SimpleDateFormat（0）--小写的yyyy和大写的YYYY会出问题（代表星期年）
         */
        //-----------------------------calender类的使用
        Calendar calendar;
        //这是一个抽象方法，不可以new对象，我们使用子类来创建
        Calendar instance = Calendar.getInstance();
        System.out.println("instance.get(Calendar.YEAR) = " + instance.get(Calendar.YEAR));
        System.out.println(instance.getTime());
        //计算一周以后是多少号
        instance.add(Calendar.WEEK_OF_MONTH,1);
        System.out.println(instance.get(Calendar.MONTH)+1);
        System.out.println(instance.get(Calendar.DATE));

        //构造自定义的时间对象
        instance.set(1999,11,23);
        System.out.println(instance.get(Calendar.YEAR));
        //格式化Calender类
        System.out.println(instance.getTime());
        /**
         * 此处有大坑二
         *就是历法的计算1582年
         */
        Date date4 = new Date("1582/1/14");

        /**
         * 新时时间类
         * LocalDate&LocalTime
         */
        System.out.println("==========================================================================");
        System.out.println(LocalDateTime.now());
        System.out.println("LocalDate.now() = " + LocalDate.now());
        System.out.println("LocalDate.of(1999,11,23) = " + LocalDate.of(1999, 11, 23));
        //a修改时间分量一
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.withMonth(3);
        System.out.println(localDate);
        System.out.println(now);//原来的时间对象是不会变的
        LocalDate localDate1 = now.withDayOfMonth(20);//修改日期
        System.out.println(localDate1);

        //修改LocalTime时间分量二
        LocalDate now1 = LocalDate.now();
        System.out.println("now1 = " + now1);
        LocalDate localDate2 = now1.plusYears(2);
        System.out.println("localDate2 = " + localDate2);

        /**
         * 日期的比较
         * ----------------------重点----------------------
         * 主要就是compareTo（）
         */
        LocalDate now2 = LocalDate.now();
        LocalDate of = LocalDate.of(2010, 8, 23);
        System.out.println(now2.compareTo(of));//返回值如果为正数。则说明前者晚于后者
        /**
         * 求两个日期相差
         * ---------------------重点----------------------
         */
        //计算到1999年1月7号相差多少天
        LocalDate now3 = LocalDate.now();
        LocalDate of1 = LocalDate.of(1999, 1, 7);
        long day = now3.toEpochDay();
        long day1 = of1.toEpochDay();
        System.out.println("(day-day1) = " + (day - day1));

        System.out.println("------------------------------------");
        System.out.println("of1.until(now3) = " + of1.until(now3));
        System.out.println("------------------------------------");
        //打印出两个时间间隔的每一天
        LocalDate now4 = LocalDate.now();
        LocalDate of2 = LocalDate.of(2021, 7, 20);
        //这个方法可以打印成Stream的天数；不知为何没有这个方法
        //Stream<LocalDate> dates=of2.datesUntil(now4);//注意now4当天不会遍历出来
        //Object[] array=dates.toArray();//就可以遍历了。
        /**
         * 判断特殊日期
         * ---------------------重点----------------------
         * 例如判断某个时间是不是某个人生日，属于周期性的日期
         */
        LocalDate now5 = LocalDate.now();
        LocalDate of3 = LocalDate.of(1999, 1, 7);
        MonthDay birthday = MonthDay.of(11, 23);
        MonthDay today = MonthDay.from(now5);//这就把now5中的年份去掉了，保留了月和日

        System.out.println("today.isBefore(of3) = " + now5.isBefore(of3));
        int tmp = today.compareTo(birthday);
        System.out.println("i = " + tmp);//如果返回是0就表明是
        //计算当前日期到下一次生日的还有多少天
        LocalDate nextBirthday;
        if(tmp<=0){//今天比birthday早意思就是说，目前还没有过今年的生日
            nextBirthday=birthday.atYear(now5.getYear());
        }else {
            //给monthDay也可以添加年份
            nextBirthday=birthday.atYear(now5.getYear());
        }
        //计算现在到下一次生日的还差多少天
        long until = now5.until(nextBirthday, ChronoUnit.DAYS);
        System.out.println("until = " + until);

        /**
         * localTime对象
         *
         */
        LocalTime now6 = LocalTime.now();
        System.out.println("now6 = " + now6);
        System.out.println("now6.get(ChronoField.MILLI_OF_SECOND) = " + now6.get(ChronoField.MILLI_OF_SECOND));
        //计算两个时间点的时间差
        LocalTime of4 = LocalTime.of(15, 0);
        long until1 = now6.until(of4, ChronoUnit.MINUTES);
        System.out.println("until1 = " + until1);
        /**
         * localDateTime对象
         *
         */
        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        LocalDateTime of5 = LocalDateTime.of(ld, lt);
        System.out.println("of5 = " + of5);

        /**
         * Period对象：表示以年月日为计时单位的时间间隔
         * Duration对象：表示以秒，纳秒为计时单位的时间间隔
         * ZoneId：时区时差
         * 。。。
         *
         */


        System.out.println(LocalDate.now());


    }

}
