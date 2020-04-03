package jd.demo.se.reflect.dynamicproxy;

import jd.demo.example.person.job.Actor;
import jd.demo.example.person.job.IActor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JAVA的动态代理
 代理模式
 代理模式是常用的java设计模式，他的特征是代理类与委托类有同样的接口，代理类主要负责为委托类预处理消息、过滤消息、把消息转发给委托类，以及事后处理消息等。代理类与委托类之间通常会存在关联关系，一个代理类的对象与一个委托类的对象关联，代理类的对象本身并不真正实现服务，而是通过调用委托类的对象的相关方法，来提供特定的服务。
 按照代理的创建时期，代理类可以分为两种。
 静态代理：由程序员创建或特定工具自动生成源代码，再对其编译。在程序运行前，代理类的.class文件就已经存在了。
 动态代理：在程序运行时，运用反射机制动态创建而成。
 * @param <T>
 */
public class DynamicProxy<T> implements InvocationHandler {

	private T subject ;
	
	
	public DynamicProxy(T subject) {
		this.subject = subject;
	}

	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable { // proxy is this target
		return method.invoke(subject, args);
	}
	public static <I,T extends I> I newProxyInstance(T target, Class<?> ... interfaces){
		if( target == null){
			throw new IllegalArgumentException("target is null");
		}
		if(interfaces == null || interfaces.length == 0){
			interfaces = target.getClass().getInterfaces();
		}
		return (I)Proxy.newProxyInstance(IActor.class.getClassLoader(),interfaces,
				new DynamicProxy<I>(target));
	}
	
	public static void main(String[] args) {
		IActor actor = newProxyInstance(new Actor("Mary"));
		actor.act();
	}

}
