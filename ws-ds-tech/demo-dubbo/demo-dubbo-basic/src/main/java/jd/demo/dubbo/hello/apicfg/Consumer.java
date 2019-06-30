package jd.demo.dubbo.hello.apicfg;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

import jd.demo.dubbo.CommonConfiguration;
import jd.demo.example.service.Greeting;
import jd.demo.example.service.impl.GreetingImpl;

public class Consumer extends CommonConfiguration{

/*	static{
		Provider.setEnv();
	}*/
	
	public static void main(String[] args) {
		ReferenceConfig<GreetingImpl> ref = new ReferenceConfig<>();
		ref.setApplication(new ApplicationConfig("dubbo-client"));
		ref.setRegistry(new RegistryConfig(Provider.MULTICAST_ADDRESS));
		ref.setInterface(Greeting.class);
		Greeting service = ref.get();
		System.out.println(service.sayHello("dubbo"));
	}

}
