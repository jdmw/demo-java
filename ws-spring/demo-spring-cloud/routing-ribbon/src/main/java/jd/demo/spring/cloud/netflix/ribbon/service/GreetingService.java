package jd.demo.spring.cloud.netflix.ribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GreetingService {
    @Value("${clientUrl}")
    String clientUrl;

    @Autowired
    RestTemplate restTemplate;

    public String hello(String name) {
        return "[ribbon]" + restTemplate.getForObject(clientUrl+"/hello?name="+name,String.class);
    }

}
