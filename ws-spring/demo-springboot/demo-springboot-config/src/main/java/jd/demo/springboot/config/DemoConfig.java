package jd.demo.springboot.cfg;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@SpringBootApplication
//@EnableAutoConfiguration 
@PropertySource(value={"classpath:cfg/demo_config1.yml","classpath:cfg/demo_config.properties"},encoding="utf-8")
public class DemoConfig {

    @Configuration
    @ConfigurationProperties(prefix="person")
    @Data
    public static class Persion {
        String cd ;
        String name ;
        List<String> hobby ;

        @Value("${person.desc}")
        String description ;

    }

    // map
    @Configuration
    @ConfigurationProperties("acme")
    @Data
    public static class AcmeProperties {
        private String name ;
        private final List<MyPojo> list = new ArrayList<>();

        private final Map<String, MyPojo> map = new HashMap<>();

        private Map<String, Map<String,MyPojo>> mmap ;

        public Map<String, MyPojo> getMap() {
            return this.map;
        }

        @Data
        public static class MyPojo {
            private String name ;
            private String description ;
        }
    }




    public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(DemoConfig.class, args);
		System.out.println(ctx.getBean(Persion.class));
		System.out.println(ctx.getBean(AcmeProperties.class).getMap());
	}

}
