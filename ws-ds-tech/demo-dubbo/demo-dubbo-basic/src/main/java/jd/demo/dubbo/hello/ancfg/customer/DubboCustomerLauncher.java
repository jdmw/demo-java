package jd.demo.dubbo.hello.ancfg.customer;

import jd.util.lang.concurrent.CcUt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;

import jd.demo.dubbo.common.service.IHelloService;
import jd.demo.dubbo.hello.ancfg.provider.DubboProviderLauncher;

// fail
@SpringBootApplication  
@DubboComponentScan(basePackages = {"jd.demo.dubbo.hello.ancfg.customer"})
public class DubboCustomerLauncher implements CommandLineRunner {

	 @com.alibaba.dubbo.config.annotation.Reference
	 public IHelloService service;
	 
	 public static void main(String[] args) {
		SpringApplication.run(DubboCustomerLauncher.class, args);
	 }

	@Override
	public void run(String... args) throws Exception {
		String rst =service.sayHello("Dubbo");
		System.out.println(rst);
	}
}
