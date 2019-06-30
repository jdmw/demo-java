package jd.demo.spring.cloud.rouing.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
public class RouingZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(RouingZuulApplication.class, args);
    }

}

