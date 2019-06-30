package jd.demo.jodd.datetime;

import jodd.datetime.JDateTime;

public class JoddDateNew {


	public static void main(String[] args) {
        JDateTime jdt = new JDateTime();            // 当前的日期以及时间
        jdt.setFormat("YYYY年MM月DD日  hh时mm分ss秒 mss毫秒");//设置格式化字符串
        System.out.println(jdt);                   //JDateTime重写了toString方法,使用了JdtFormatter来格式化输出字符串
        
        jdt = new JDateTime(2018, 12, 21);        // 2012年12月21日
        System.out.println(jdt.toString());
        
        jdt = new JDateTime(System.currentTimeMillis());    // 根据当前系统毫秒说来获取时间
        System.out.println(jdt.toString("MM/DD/YYYY hh:mm:ss mss"));//toString还有一个JdtFormat做完参数的方法，但是不太方便
        
        jdt = new JDateTime(2012, 12, 21, 11, 54, 22, 124); // 设置日期以及时、分、秒、毫秒
        System.out.println(jdt);
        jdt = new JDateTime("2012-12-21 11:54:22.124");     // 使用格式化字符串设置时间
        System.out.println(jdt);
        
        jdt = new JDateTime("12/21/2012", "MM/DD/YYYY");    // 使用自己定义的时间字符串设置时间
        System.out.println(jdt);
        
        
        System.out.println(jdt.toString("YYYY.MM.DD")); 
    }
}
