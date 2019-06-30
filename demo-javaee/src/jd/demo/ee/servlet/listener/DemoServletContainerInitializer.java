package jd.demo.ee.servlet.listener;

import java.util.Set;
import java.util.logging.Filter;

import javax.servlet.Servlet;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

import jd.util.lang.Console;

@HandlesTypes({Servlet.class,Filter.class})
public class DemoServletContainerInitializer implements ServletContainerInitializer {

	public DemoServletContainerInitializer() {
	    Console.ln("ServletContext:  new DemoServletContainerInitializer()");
	}
	
	@Override
	public void onStartup(Set<Class<?>> arg0, ServletContext arg1) throws ServletException {
		Console.ln("application startup : ",arg0);
	}

}
