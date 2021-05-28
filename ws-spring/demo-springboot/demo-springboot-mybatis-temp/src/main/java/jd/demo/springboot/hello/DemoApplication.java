package jd.demo.springboot.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// banner off
		// new SpringApplicationBuilder().bannerMode(Banner.Mode.OFF).sources(DemoApplication.class).run( args);
		SpringApplication.run(DemoApplication.class,args);
	}
}
