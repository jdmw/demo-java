package jd.demo.dubbo.hello.ancfg.provider;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import jd.util.lang.concurrent.CcUt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;

@SpringBootApplication  
@DubboComponentScan(basePackages = {/*"jd.demo.dubbo.hello.ancfg.provider",*/"jd.demo.dubbo.common.service.impl"})
public class DubboProviderLauncher  {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(DubboProviderLauncher.class, args);
		System.in.read();
	}

	public static void startInDeamonThread(){
		CcUt.start(()->SpringApplication.run(DubboProviderLauncher.class,new String[0]),true);
	}

}
