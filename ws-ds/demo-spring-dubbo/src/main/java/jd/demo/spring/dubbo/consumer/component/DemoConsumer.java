package jd.demo.spring.dubbo.consumer.component;

import jd.demo.example.service.Greeting;
import jd.util.ArrUt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DemoConsumer implements CommandLineRunner {

    @Resource
    private Greeting greeting;

    @Override
    public void run(String... args) throws Exception {
        String name = ArrUt.get(args,0,"Alice");
        System.out.println(greeting.sayHello(name));
    }
}
