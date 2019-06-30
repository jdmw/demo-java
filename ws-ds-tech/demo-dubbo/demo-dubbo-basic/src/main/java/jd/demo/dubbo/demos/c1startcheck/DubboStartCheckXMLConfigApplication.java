package jd.demo.dubbo.demos.c1startcheck;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import jd.demo.dubbo.CommonConfiguration;
import jd.demo.example.service.Greeting;

public class DubboStartCheckXMLConfigApplication extends CommonConfiguration{

	static {
		initRemote();
	}
	
	public static void initRemote(){
		ClassPathXmlApplicationContext ctx = loadSpringContainer(DubboStartCheckXMLConfigApplication.class, "dubbo-provider.xml");
		ctx.start();
	}

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext ctx = CommonConfiguration.loadSpringContainer(DubboStartCheckXMLConfigApplication.class, "dubbo-consumer.xml");
		ctx.start();
		
		Greeting greeting = (Greeting) ctx.getBean("greeting");
		long before = System.nanoTime();
		String result = greeting.sayHello("dubbo") ;
		long timeElapsed = System.nanoTime() - before;
		System.out.println(result + " | timeElapsed:" + timeElapsed  + "ns");
	}

}
