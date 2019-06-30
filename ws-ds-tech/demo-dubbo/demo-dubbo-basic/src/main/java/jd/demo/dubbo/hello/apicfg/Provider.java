package jd.demo.dubbo.hello.apicfg;

import java.io.IOException;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

import jd.demo.dubbo.CommonConfiguration;
import jd.demo.example.service.Greeting;
import jd.demo.example.service.impl.GreetingImpl;

/**
 * @see reference at https://mp.weixin.qq.com/s/bcwIMIir2RHPbQQr8HgTOQ
 * @author jdmw
 *
 */
public class Provider extends CommonConfiguration{


	public static void main(String[] args) throws IOException {
		
		ServiceConfig<Greeting> service = new ServiceConfig<>();
		service.setApplication(new ApplicationConfig("dubbo-provider"));
		service.setRegistry(new RegistryConfig(MULTICAST_ADDRESS));
		service.setInterface(Greeting.class);
		service.setRef(new GreetingImpl());
		service.export();
		// wait
		System.in.read();
		
		
		
		//default port: listen at 20880
	}
	
}
