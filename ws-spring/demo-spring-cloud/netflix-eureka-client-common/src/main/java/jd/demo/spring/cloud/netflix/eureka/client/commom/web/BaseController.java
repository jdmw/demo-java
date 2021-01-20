package jd.demo.spring.cloud.netflix.eureka.client.commom.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    @Value("${server.port}")
    String port;

    @GetMapping("/hello")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name) {
        return "hi " + name + " ,i am from port:" + port;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }

}
