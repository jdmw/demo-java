package jd.demo.ee.servlet.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import jd.util.lang.Console;

/**
 * Application Lifecycle Listener implementation class DemoHttpSessionBindingListener
 *
 */
@WebListener
public class DemoHttpSessionBindingListener implements HttpSessionBindingListener {

    /**
     * Default constructor. 
     */
    public DemoHttpSessionBindingListener() {
    	Console.tpln("[%t]session: new DemoHttpSessionBindingListener() ");
    }

    /**
     * @see HttpSessionBindingListener#valueBound(HttpSessionBindingEvent)
     */
    public void valueBound(HttpSessionBindingEvent event)  { 
         Console.tpln("[%t]session valueBound ");
    }
    
	/**
     * @see HttpSessionBindingListener#valueUnbound(HttpSessionBindingEvent)
     */
    public void valueUnbound(HttpSessionBindingEvent event)  { 
         Console.tpln("[%t]session valueUnbound ");
    }
	
}
