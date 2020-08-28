package jd.demo.springboot.cfg;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;


@Configuration
//@Component
@ConfigurationProperties(prefix="person")
@Data
class Persion {
	String cd ;
	String name ;
	List<String> hobby ;
	
	@Value("${person.desc}")
	String description ;

}

@SpringBootApplication
//@EnableAutoConfiguration 
@PropertySource(value="classpath:cfg/demo_config.properties",encoding="utf-8")
public class DemoConfig {

	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(DemoConfig.class, args);
		System.out.println(ctx.getBean(Persion.class));
	}

}
