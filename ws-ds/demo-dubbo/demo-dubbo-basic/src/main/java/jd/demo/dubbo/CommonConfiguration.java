package jd.demo.dubbo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;

//@Configuration
public class CommonConfiguration {

	public static String MULTICAST_ADDRESS = "multicast://224.5.6.7:1234" ;
	public static String ZOOKEEPER_ADDRESS = "localhost:2181" ;
	public static String CLIENT_NAME = "curator" ;
	
	private String appName  ;
	
	static{
		setEnv();
	}

	public static void setEnv() {
		System.setProperty("java.net.preferIPv4Stack=true", "true");
	}
	
	
	public static String getCfgPath(Class<?> clz,String xmlName) {
		return clz.getPackage().getName().replaceAll("\\.", "/")+"/"+ xmlName ;
	}
	
	public static ClassPathXmlApplicationContext loadSpringContainer(Class<?> clz,String ...xmlNames) {
		String base = clz.getPackage().getName().replaceAll("\\.", "/")+"/";
		String[] cfgPath = new String[xmlNames.length];
		for(int i=0;i<xmlNames.length;i++) {
			cfgPath[i] = base + xmlNames[i];
		}
		return new ClassPathXmlApplicationContext (cfgPath);
	}
	
/*	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/

	
	@Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(this.getAppName());
        return applicationConfig;
    }


    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(3000);
        return consumerConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setClient("curator");
        return registryConfig;
    }


	public String getAppName() {
		return appName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}
}
