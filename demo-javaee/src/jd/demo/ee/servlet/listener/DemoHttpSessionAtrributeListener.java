package jd.demo.ee.servlet.listener;

import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import jd.util.lang.Console;


@WebListener
public class DemoHttpSessionAtrributeListener implements HttpSessionAttributeListener {

	/**
     * Default constructor. 
     */
    public DemoHttpSessionAtrributeListener() {
        Console.tpln("[%t]session attr: new DemoHttpSessionAtrributeListener()");
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent e)  { 
    	Console.tpln("[%t]session attr add : %s - %s , session.id = %s " , 
    			e.getName(),e.getValue(),e.getSession().getAttribute(e.getName()) );
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent e)  { 
    	Console.tpln("[%t]session attr remove : %s - %s , session.id = %s " , 
    			e.getName(),e.getValue(),e.getSession().getAttribute(e.getName()) );
    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent e)  { 
    	Console.tpln("[%t]session attr replaced : %s - %s , session.id = %s " , 
    			e.getName(),e.getValue(),e.getSession().getAttribute(e.getName()) );
    }
    

  
}
