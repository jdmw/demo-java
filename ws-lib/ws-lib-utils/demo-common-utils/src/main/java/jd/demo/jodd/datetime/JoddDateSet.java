package jd.demo.jodd.datetime;

import jodd.datetime.JDateTime;

public class JoddDateSet {


	public static void main(String[] args) {
        JDateTime jdt = new JDateTime();            // 当前的日期以及时间
        jdt.setTime(0, 0, 0, 0);
        System.out.println(jdt);
        
        
        int day = 2 ;
        jdt = new JDateTime(2018,1,31);
        jdt.setFormat("YYYY-MM-DD");
        System.out.print(jdt + "->add " + day + " day(s):");
        jdt.addDay(day);
        System.out.println(jdt);
        
        
        int month = 1 ;
        jdt = new JDateTime(2018,1,31);
        jdt.setFormat("YYYY-MM-DD");
        System.out.print(jdt + "->month " + month + " month(s):");
        jdt.addMonth(month);
        System.out.println(jdt);
        
        
        jdt = new JDateTime(2018,1,31);
        jdt.setFormat("YYYY-MM-DD");
        System.out.print(jdt + "->month " + month + " month(s) and fix month:");
        jdt.setMonthFix(true);
        jdt.addMonth(month);
        System.out.println(jdt);
        /*
        System.out.println(jdt);
        
        
        System.out.println(jdt);*/
    }
}
