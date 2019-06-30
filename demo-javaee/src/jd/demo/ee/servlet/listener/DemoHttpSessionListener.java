package jd.demo.ee.servlet.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

import jd.util.lang.Console;

/**
 * Application Lifecycle Listener implementation class DemoHttpSessionListener
 *
 */
@WebListener
public class DemoHttpSessionListener implements HttpSessionListener, HttpSessionActivationListener,HttpSessionIdListener {

    /**
     * Default constructor. 
     */
    public DemoHttpSessionListener() {
        Console.ln("Session: new DemoHttpSessionListener()");
    }
	
	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
         Console.ln("session has been Created ",se.getSession()," ,sessionId=",se.getSession().getId());
    }

	
	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
         Console.ln("session is about to be invalidated ",se.getSession()," ,sessionId=",se.getSession().getId());
    }

    
	/**
     * @see HttpSessionActivationListener#sessionDidActivate(HttpSessionEvent)
     */
    public void sessionDidActivate(HttpSessionEvent se)  { 
         Console.ln("session Did Activate ",se.getSession()," ,sessionId=",se.getSession().getId());
    }

	/**
     * @see HttpSessionActivationListener#sessionWillPassivate(HttpSessionEvent)
     */
    public void sessionWillPassivate(HttpSessionEvent se)  { 
         Console.ln("session Will Passivate ",se.getSession()," ,sessionId=",se.getSession().getId());
    }

	@Override
	public void sessionIdChanged(HttpSessionEvent se, String id) {
		Console.tpln("session change id  %s  %s [%t]",se.getSession().getId(),id);
	}
	
}
