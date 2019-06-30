package jd.demo.dubbo.hello.ancfg.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ConsumerConfig;

@Configuration
public class DubboConfiguration extends jd.demo.dubbo.CommonConfiguration{

    public DubboConfiguration(){
        super.setAppName("dubbo-customer");
    }
/*
	@Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(this.getAppName());
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(CommonConfiguration.MULTICAST_ADDRESS);
        registryConfig.setClient("curator");
        return registryConfig;
        
		return new RegistryConfig(CommonConfiguration.MULTICAST_ADDRESS);
    }*/
    
    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(3000);
        return consumerConfig;
    }
}