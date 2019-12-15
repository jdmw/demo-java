package jd.demo.spring.framework.core.anotations.autowired;


import jd.demo.spring.framework.DemoSpringFrameworkApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.boot.SpringApplication.*;
class Animal{}

@Component
class Cat  extends Animal{ }

@Component
class Dog  extends Animal{
}

@Component
class Pig  extends Animal{
}



@Configuration
@ComponentScan(basePackages = "jd.demo.spring.framework.core.anotations.autowired")
@SpringBootConfiguration
@EnableAutoConfiguration
public class DemoAutowired implements CommandLineRunner {

    @Autowired // 等同 @Resource @Inject
    private Cat cat ;
    private Dog dog ;
    private Pig pig;
    @Autowired
    private Pig[] pigs ;
    @Autowired
    private Set<Animal> animalSet ;
    @Autowired
    private List<Animal> animalList;
    @Autowired
    private Map<String,Animal> stringAnimalMap ;

    @Resource
    public void setDog(Dog dog) {
        this.dog = dog;
    }

    @Autowired
    public void setPig(@Qualifier("pig") Pig pig) {
        this.pig = pig;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoAutowired.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(args);
    }
}
