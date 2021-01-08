package jd.demo.spring.framework.core;

import jd.demo.example.common.thingsbysubject.biology.creature.animal.Animals;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jdmw on 2019/12/16.
 */
@Configuration
@ComponentScan(basePackages = "jd.demo.spring.framework.core")
@SpringBootConfiguration
@EnableAutoConfiguration
@ToString
public class DemoDependencyInjection implements CommandLineRunner {


    @Autowired
    private Animals.IFish fish ;

    @Autowired
    @Qualifier("tuna")
    private Animals.IFish tuna ;

    public static void main(String[] args) {
        SpringApplication.run(DemoDependencyInjection.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(this);
    }
}