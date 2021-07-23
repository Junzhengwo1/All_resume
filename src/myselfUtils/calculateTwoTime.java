package myselfUtils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class calculateTwoTime {

    /**
     * 计算两个时间的间隔，
     * @param fromDate
     * @param toDate
     * @return
     */
    private  String calculateTwoTimeSpace(Date fromDate, Date toDate){
        LocalDate from = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate to = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period between = Period.between(from, to);
        return between.getYears()+"年"+between.getMonths()+"月"+between.getDays()+"天";
    }

}
