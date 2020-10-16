package jd.demo.se.bypkg.util.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by huangxia on 2019/11/7.
 */
public class DemoCalendar {

    public static void main(String[] args) throws ParseException{
        Calendar calendar = Calendar.getInstance();
        /*System.out.printf("today DAY_OF_MONTH %d\n",calendar.get(Calendar.DAY_OF_MONTH));
        System.out.printf("last day of this month %d\n",calendar.getMaximum(Calendar.DAY_OF_MONTH));
        System.out.printf("today DAY_OF_WEEK %d\n",calendar.get(Calendar.DAY_OF_WEEK));
        System.out.printf("last day of this week %d\n",calendar.getMaximum(Calendar.DAY_OF_WEEK));*/
        //calendar.add(20,Calendar.DAY_OF_WEEK);
        System.out.println(getZeroHmsDate(calendar.getTime()));
    }

    private static Date getZeroHmsDate(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00:000");
        return sdf.parse(sdf.format(date));
        /*Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(0,Calendar.HOUR);
        calendar.set(0,Calendar.MINUTE);
        calendar.set(0,Calendar.SECOND);
        calendar.set(0,Calendar.MILLISECOND);
        return calendar.getTime();*/
    }

    public static int differentDaysByDate(Date date1, Date date2) throws ParseException {
        int days = (int) ((getZeroHmsDate(date2).getTime() - getZeroHmsDate(date1).getTime()) / (1000*3600*24));
        return days;
    }

}
