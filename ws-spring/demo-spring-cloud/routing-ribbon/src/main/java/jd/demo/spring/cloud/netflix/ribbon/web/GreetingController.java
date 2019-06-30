package jd.demo.spring.cloud.netflix.ribbon.web;

import jd.demo.spring.cloud.netflix.ribbon.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @Autowired
    GreetingService geetingService;

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "forezp") String name) {
        return this.geetingService.hello(name);
    }

}
