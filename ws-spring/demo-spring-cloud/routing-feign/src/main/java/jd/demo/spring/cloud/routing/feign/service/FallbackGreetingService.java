package jd.demo.spring.cloud.routing.feign.service;

public class FallbackGreetingService implements  GreetingService {

    @Override
    public String hello(String name) {
        return "error," + name;
    }
}
