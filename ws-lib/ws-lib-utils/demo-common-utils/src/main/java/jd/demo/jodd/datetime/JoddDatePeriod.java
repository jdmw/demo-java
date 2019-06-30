package jd.demo.jodd.datetime;

import java.time.LocalDate;
import java.time.Period;

import jodd.datetime.JDateTime;

public class JoddDatePeriod {

	public static void main(String[] args) {
		LocalDate jdt=new JDateTime("2015-7-14").convertToSqlDate().toLocalDate();
		LocalDate jdtTow=new JDateTime("2016-7-14").convertToSqlDate().toLocalDate();
        Period period=Period.between(jdt,jdtTow);
        System.out.println(period.getDays()); //获取两个时间中天数部分的差

	}

	
}
