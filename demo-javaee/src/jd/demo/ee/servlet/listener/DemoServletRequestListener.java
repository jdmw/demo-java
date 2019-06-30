package jd.demo.ee.servlet.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

import jd.util.lang.Console;

/**
 * Application Lifecycle Listener implementation class DemoServletContextListener
 *
 */
@WebListener
public class DemoServletRequestListener implements ServletRequestListener {

    /**
     * Default constructor. 
     */
    public DemoServletRequestListener() {
    	Console.ln("Request:  new DemoServletContextListener()");
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent sre)  { 
         Console.ln("Request is about to be shut down");
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent sre)  { 
    	Console.ln("Request is starting");
    }
	
}
