package com.kou.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;

public class MyDateUtil {

    @Autowired
    private UtilProperties utilProperties;

    public String getLocalTime(){
        int zone = 0 ;

        if (null != utilProperties.getLatitude()){
            zone = (int) Math.round((utilProperties.getLatitude()* DateTimeConstants.HOURS_PER_DAY) / 3600);
        }else {
            zone = utilProperties.getZone();
        }

        DateTimeZone dz = DateTimeZone.forOffsetHours(zone);

        return new DateTime(dz).toString(utilProperties.getPattern());
    }

}
