package jd.demo.ee.servlet.request;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jd.util.lang.Console;

/**
 * Servlet implementation class DemoRequest_ParseHtml
 */
@WebServlet("/request/parsehtml")
public class DemoRequest_ParseHtml extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DemoRequest_ParseHtml() {
    	Console.ln("servlet: new DemoRequest_ParseHtml()" );
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
/*		 Console.ln("\nprotocol:",req.getProtocol());  
		 Console.ln("\nmethod:",req.getMethod()); 
		 //ServletInputStream in = req.getInputStream();
		 //Console.ln("ParameterMap:",req.getParameterMap());
		 //Console.ln("Parameter name=",req.getParameter("name"));
		 //Console.ln("postdata:",in);
		 Console.fmt("uri:%s,\nurl:%s\n pathinfo:%s,path translated:%s\n queryString:%s\n\n", 
				 req.getRequestURI(),req.getRequestURL(),req.getPathInfo(),req.getPathTranslated(),req.getQueryString());
		 invokeRequestAllMethod(req);*/
		 response.getWriter().println("OK");
	}

	private void invokeRequestAllMethod(HttpServletRequest req) {
		for(Field f : req.getClass().getFields()) {
			try {
				Console.ln(f.getName()," = ",f.get(req));
			} catch (Throwable e) {
				Console.error("get ",f.getName()," error ");
			}
		}
		List<Method> ServletRequestMethods = new ArrayList<>();
		List<Method> remainedServletRequestMethods = new ArrayList<>();
		List<Method> remainedHttpServletRequestMethods = new ArrayList<>();
		
		Console.ln("\nServletRequest methods:");
		for(Method m : ServletRequest.class.getMethods()) {
			if(m.getParameterTypes().length == 0) {
				if("startAsync".equals(m.getName())) {
					continue;
				}
				try {
					Console.ln(m.getName()," (): ",m.invoke(req));
				} catch (Throwable e) {
					Console.error(m.getName(),"() : error ");
				}
			}else {
				remainedServletRequestMethods.add(m);
			}
			ServletRequestMethods.add(m);
		}
		Console.ln("\nUnused Methods:");
		for(Method m:  remainedServletRequestMethods) {
			Console.ln(m.getReturnType().getSimpleName()," ",m.getName(),"(",m.getParameterTypes(),")");
		}
		
		Console.ln("\nHttpServletRequest methods:");
		for(Method m : HttpServletRequest.class.getDeclaredMethods()) {
			if(!ServletRequestMethods.contains(m)) {
				if(m.getParameterTypes().length == 0) {
					try {
						Console.ln(m.getName()," (): ",m.invoke(req));
					} catch (Throwable e) {
						Console.error(m.getName(),"() : error ");
					}
				}else {
					remainedHttpServletRequestMethods.add(m);
				}
			}
		}
		Console.ln("\nUnused Methods:");
		for(Method m:  remainedHttpServletRequestMethods) {
			Console.ln(m.getReturnType().getSimpleName()," ",m.getName(),"(",m.getParameterTypes(),")");
		}
		
		
	}
	public void test1(HttpServletRequest req, HttpServletResponse response) throws IOException {
		ServletInputStream in = req.getInputStream();
		Console.ln("postdata:",in);
		Console.fmt("uri:%s,url:%s \npathinfo:%s, path translated:%s \nqueryString:%s\n", 
				 req.getRequestURI(),req.getRequestURL(),req.getPathInfo(),req.getPathTranslated(),req.getQueryString());
	}
	/**
	 * result : /demo-javaee-servlet/request/parsehtml?name=param&city=value
	 *  (1) post
	 * 	postdata:name=param&city=value
		uri:/demo-javaee-servlet/request/parsehtml,
		url:http://localhost:8910/demo-javaee-servlet/request/parsehtml
		pathinfo:null,path translated:null
		queryString:null
		(2)get
		postdata:<empty>
		uri:/demo-javaee-servlet/request/parsehtml,
		url:http://localhost:8910/demo-javaee-servlet/request/parsehtml
		pathinfo:null,path translated:null
		queryString:name=param&city=value

	 */
}
