package jd.demo.spring.cloud.routing.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="netflix-eureka-client", fallbackFactory=FallbackGreetingService.class)
public interface GreetingService {
    @RequestMapping("/hello")
    String hello(String name);
}
