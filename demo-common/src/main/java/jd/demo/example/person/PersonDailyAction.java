package jd.demo.example.person;

import jd.util.lang.Console;

public class PersonDailyAction {

	private String name ;
	public PersonDailyAction(String name) {
		this.name = name ;
	}
	
	public void action(String action) {
		Console.ln(name,action);
	}
}
