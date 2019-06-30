package jd.demo.spring.dubbo.provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@PropertySource({"dubbo-provider.properties"})
@SpringBootApplication(scanBasePackages="jd.demo.spring.dubbo.provider.service")
//@EnableDubboConfiguration
public class DubboProviderLauncher {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderLauncher.class, args);
    }
}