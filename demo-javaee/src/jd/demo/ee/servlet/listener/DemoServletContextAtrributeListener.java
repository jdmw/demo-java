package jd.demo.ee.servlet.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

import jd.util.lang.Console;

/**
 * Application Lifecycle Listener implementation class DemoServletContextAtrributeListener
 *
 */
@WebListener
public class DemoServletContextAtrributeListener implements ServletContextAttributeListener {

    /**
     * Default constructor. 
     */
    public DemoServletContextAtrributeListener() {
        Console.tpln("[%t]ServletContext attr: new DemoServletContextAtrributeListener()");
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    public void attributeAdded(ServletContextAttributeEvent e)  { 
    	Console.tpln("[%t]ServletContext attr add :" + e.getName());
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    public void attributeRemoved(ServletContextAttributeEvent e)  { 
    	Console.tpln("[%t]ServletContext attr remove :" + e.getName());
    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    public void attributeReplaced(ServletContextAttributeEvent e)  { 
    	Console.tpln("[%t]ServletContext attr replaced :" + e.getName() + "->" + e.getValue());
    }
	
}
