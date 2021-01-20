package jd.demo.spring.cloud.netflix.eureka.client.a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class NetflixEurekaClientA {
    public static void main(String[] args) {
        //org.springframework.boot.context.properties.ConfigurationBeanFactoryMetadata d ;
        //com.netflix.discovery.DiscoveryClient l ;
        //EurekaHttpClientDecorator eurekaHttpClientDecorator;
        SpringApplication.run(NetflixEurekaClientA.class, args);
    }

}


