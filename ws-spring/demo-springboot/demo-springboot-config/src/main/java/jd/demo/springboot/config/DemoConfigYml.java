
package jd.demo.springboot.cfg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.util.List;


@SpringBootApplication
//@EnableAutoConfiguration 
@PropertySource(value="classpath:cfg/demo_config_yml.yml",encoding="utf-8")
public class DemoConfigYml {

	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(DemoConfigYml.class, args);
		List sqls = ctx.getEnvironment().getProperty("sqls", List.class);
		System.out.println(sqls);

	}

}
