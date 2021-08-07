package myselfUtils.CalceAll;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ShuziUtils {
    //保留两位小数
    private String getKeepTwoDecimal(Double d) {
        if (d == 0) return "0.00";
        return new java.text.DecimalFormat("#.00").format(d);
    }


    //增加一天
    private static Date addOneDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }


    //判断两个时间是否相等
    private static boolean equalDate(Date start, Date end) {
        Format f = new SimpleDateFormat("yyyy-MM-dd");

        String date1 = f.format(start);
        String date2 = f.format(end);
        return date1.equals(date2);
    }



}
