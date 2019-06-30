package jd.demo.example.person.job;

import jd.util.lang.Console;

public class Actor implements IActor {

	private String name ;

	public Actor(String name) {
		this.name = name;
	}



	@Override
	public void act() {
		Console.ln(name," is acting");
	}
	
}
