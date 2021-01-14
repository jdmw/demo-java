package jd.demo.spring.cloud.netflix.eureka.client;

import com.netflix.discovery.shared.transport.decorator.EurekaHttpClientDecorator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class NetflixEurekaClientApplication {
    public static void main(String[] args) {
        com.netflix.discovery.DiscoveryClient l ;

        EurekaHttpClientDecorator eurekaHttpClientDecorator;
        SpringApplication.run(NetflixEurekaClientApplication.class, args);
    }

}


