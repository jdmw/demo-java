package jd.demo.jfinal.controller;

import com.jfinal.core.Controller;

public class GreetingController extends Controller{

	public void index() {
		super.renderText("Hello!");
	}
	
	public void bye() {
		super.renderText("Good Bye!") ;
	}
}
