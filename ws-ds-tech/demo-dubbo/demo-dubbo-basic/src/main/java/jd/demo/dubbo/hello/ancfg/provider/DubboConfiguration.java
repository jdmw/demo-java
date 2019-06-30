package jd.demo.dubbo.hello.ancfg.provider;

import org.springframework.context.annotation.Configuration;

@Configuration 
public class DubboConfiguration extends jd.demo.dubbo.CommonConfiguration {

	@Override
	public String getAppName() {
		return "dubbo-provider";
	}
}