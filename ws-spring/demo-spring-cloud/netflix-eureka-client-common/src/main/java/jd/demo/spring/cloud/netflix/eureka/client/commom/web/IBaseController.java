package jd.demo.spring.cloud.netflix.eureka.client.commom.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by huangxia on 2008/1/20.
 */
public interface IBaseController {

    @GetMapping("/hello")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name);

    @GetMapping("/health")
    public String healthCheck();

}
