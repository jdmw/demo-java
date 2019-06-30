package jd.demo.ee.servlet.listener;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

import jd.util.lang.Console;

/**
 * Application Lifecycle Listener implementation class DemoServletContextListener
 *
 */
@WebListener
public class DemoServletContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public DemoServletContextListener() {
    	Console.ln("ServletContext:  new DemoServletContextListener()");
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent sre)  { 
         Console.ln("ServletContext is about to be shut down");
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent sre)  { 
    	Console.ln("ServletContext is starting");
    }
	
}
