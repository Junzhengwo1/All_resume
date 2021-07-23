package DateTimeAll;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

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

    }

}
