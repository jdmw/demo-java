package jd.demo.spring.framework;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import static org.springframework.boot.SpringApplication.*;

@Component
class Hello {
    public Hello(){
        System.out.println();
    }
}
@SpringBootApplication
public class DemoSpringFrameworkApplication {


    public static void main(String[] args) {
        run(DemoSpringFrameworkApplication.class, args);
    }

}
