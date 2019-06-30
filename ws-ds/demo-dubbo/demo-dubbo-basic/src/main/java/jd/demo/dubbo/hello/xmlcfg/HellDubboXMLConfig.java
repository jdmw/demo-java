package jd.demo.dubbo.hello.xmlcfg;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import jd.demo.dubbo.CommonConfiguration;
import jd.demo.example.service.Greeting;

public class HellDubboXMLConfig extends CommonConfiguration{

	static {
		initRemote();
	}
	
	public static void initRemote(){
		ClassPathXmlApplicationContext ctx = CommonConfiguration.loadSpringContainer(HellDubboXMLConfig.class, "dubbo-provider.xml");
		ctx.start();
	}

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext ctx = CommonConfiguration.loadSpringContainer(HellDubboXMLConfig.class, "dubbo-consumer.xml");
		ctx.start();
		
		Greeting greeting = (Greeting) ctx.getBean("greeting");
		long before = System.nanoTime();
		String result = greeting.sayHello("dubbo") ;
		long timeElapsed = System.nanoTime() - before;
		System.out.println(result + " | timeElapsed:" + timeElapsed  + "ns");
	}

}
