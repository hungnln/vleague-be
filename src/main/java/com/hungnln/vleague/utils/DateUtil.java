package com.hungnln.vleague.utils;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
@Service
public class DateUtil {
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final SimpleDateFormat INPUT_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");
    public java.util.Date parseDate(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
    public java.util.Date parseDateTime(String date) {
        try {
            return DATE_TIME_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
    public java.util.Date  parseInputDateTime(String date){
        try {
            return INPUT_DATE_TIME_FORMAT.parse(date.replace(" ", " +"));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private java.util.Date parseTimestamp(String timestamp) {
        try {
            return DATE_TIME_FORMAT.parse(timestamp);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
    public String formatDate(java.util.Date date){
        DATE_FORMAT.setTimeZone(TIME_ZONE);
        return DATE_FORMAT.format(date);
    }
    public String formatDateTime(java.util.Date date){
        DATE_TIME_FORMAT.setTimeZone(TIME_ZONE);
        return DATE_TIME_FORMAT.format(date);
    }
    public String formatInputDateTime(java.util.Date date){
        INPUT_DATE_TIME_FORMAT.setTimeZone(TIME_ZONE);
        return DATE_TIME_FORMAT.format(date);
    }
    public String formatInputDate(String dateString){
        try {
            INPUT_DATE_TIME_FORMAT.setTimeZone(TIME_ZONE);
            Date date = INPUT_DATE_TIME_FORMAT.parse(dateString.replace(" ","Z"));
            return formatDateTime(date);
        }catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    };
}
