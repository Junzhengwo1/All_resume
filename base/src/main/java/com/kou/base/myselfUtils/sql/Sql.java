package com.kou.base.myselfUtils.sql;

public class Sql {

//    SELECT a.verify_result as descName,
//    COUNT(verify_result) as statisticNum
//    FROM admin_alarm_list a
//    ${ew.customSqlSegment}
//        <choose>
//            <when test="time.cycle == 'DAY'">
//    AND date_format(alarm_time, '%Y-%m-%d') &gt;= #{time.startStr}
//    AND date_format(alarm_time, '%Y-%m-%d') &lt;= #{time.endStr}
//            </when>
//            <when test="time.cycle == 'MONTH'">
//    AND date_format(alarm_time, '%Y-%m') &gt;= #{time.startStr}
//    AND date_format(alarm_time, '%Y-%m') &lt;= #{time.endStr}
//            </when>
//            <when test="time.cycle == 'SEASON'">
//    AND CONCAT(YEAR(alarm_time),'-',QUARTER(alarm_time)) &gt;= #{time.startStr}
//    AND CONCAT(YEAR(alarm_time),'-',QUARTER(alarm_time)) &lt;= #{time.endStr}
//            </when>
//            <when test="time.cycle == 'YEAR'">
//    AND date_format(alarm_time, '%Y') &gt;= #{time.startStr}
//    AND date_format(alarm_time, '%Y') &lt;= #{time.endStr}
//            </when>
//        </choose>
//    GROUP BY verify_result
//    

}
