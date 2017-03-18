package com.testo;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by sonu on 8/8/16.
 */
public class MyCurrentDate {
    public static DateTime getCurrentDate() {
        return  new DateTime(DateTimeZone
                .forTimeZone(TimeZone.getTimeZone("UTC")));
    }

    public static String getTime(String temp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateGet = temp;
        Date date = null;
        String bankTime;
        try {
            date = sdf.parse(dateGet);
            long st = date.getTime();
            bankTime = String.valueOf(st);
        } catch (ParseException e) {
            bankTime="-1";
            e.printStackTrace();
        }
        return bankTime;
    }

    public static String getParsedDate(String user_dob, String toDateFormat, String fromDateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromDateFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date;
        try {
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat(toDateFormat);
            date = sdf.parse(user_dob);
            dateFormatGmt.setTimeZone(TimeZone.getDefault());
            String formattedDate = dateFormatGmt.format(date);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return user_dob;
    }


}
