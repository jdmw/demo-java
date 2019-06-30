package jd.demo.ee.servlet.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jd.util.lang.Console;

/**
 * Servlet implementation class DemoRequest_ParseHtml
 */
@WebServlet("/request/changeAttributes")
public class DemoRequest_changeAttributes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DemoRequest_changeAttributes() {
    	Console.ln("servlet: new DemoRequest_changeAttributes()" );
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		req.getSession().removeAttribute("name");
		Console.tpln("[%t]removeSessionAttr:%s ","name");
		
		setSessionAttr(req,"name", null);
		setSessionAttr(req,"name", "value");
		setSessionAttr(req,"name", "newValue");
		setSessionAttr(req,"name", null);
		
		Console.tpln("\nsession: before changing,session is %s [%t]",req.getSession().getId());
		req.changeSessionId();
		Console.tpln("session: after changing,session is %s [%t]",req.getSession().getId());
		response.getWriter().println("OK");
	}

	public synchronized void setSessionAttr(HttpServletRequest req,String name,String value) {
		req.getSession().setAttribute(name, value);
		Console.tpln("[%t]setSessionAttr:%s = %s",name,value);
	}
}
