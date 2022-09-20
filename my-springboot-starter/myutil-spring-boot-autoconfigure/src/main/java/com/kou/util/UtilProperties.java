package com.kou.util;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * util 属性类
 */
@Configuration
@ConfigurationProperties(prefix = "util.my.date")
public class UtilProperties {

    // 根据经度算当地时间

    private Double latitude;

    private int zone = 8;

    private String pattern = "yyyy-MM-dd hh:mm:ss";

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
