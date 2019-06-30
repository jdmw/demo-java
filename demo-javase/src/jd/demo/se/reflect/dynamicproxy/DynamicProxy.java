package jd.demo.se.reflect.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import jd.demo.example.person.job.Actor;
import jd.demo.example.person.job.IActor;

public class DynamicProxy<T> implements InvocationHandler {

	private T subject ;
	
	
	public DynamicProxy(T subject) {
		this.subject = subject;
	}

	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable { // proxy is this instance
		return method.invoke(subject, args);
	}
	
	
	public static void main(String[] args) {
		IActor actor = (IActor) Proxy.newProxyInstance(IActor.class.getClassLoader(),new Class[] {IActor.class}, 
				new DynamicProxy<IActor>(new Actor("Mary")));
		actor.act();
	}

}
