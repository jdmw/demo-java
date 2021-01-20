package jd.demo.spring.cloud.netflix.eureka.client.a.web;

import com.alibaba.fastjson.JSON;
import feign.ReflectiveFeign;
import jd.demo.spring.cloud.netflix.eureka.client.a.feign.IFeignClientB;
import jd.demo.spring.cloud.netflix.eureka.client.commom.constants.Providers;
import jd.demo.spring.cloud.netflix.eureka.client.commom.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class GreetController extends BaseController {

    @Autowired
    private IFeignClientB clientB ;

    @GetMapping(value = "/nodes")
    public String allNodes() throws Exception {
        ReflectiveFeign feign ;
        Map<String,String> result = new HashMap();

        // feign client
        // step 1: build template:
        // feign.ReflectiveFeign.BuildTemplateByResolvingArgs.create
        // step 2: ms load balance, default: round robin
        FeignBlockingLoadBalancerClient loadBalancerClient = null;
        RoundRobinLoadBalancer roundRobinLoadBalancer = null;
        if (loadBalancerClient != null && roundRobinLoadBalancer != null) {
            loadBalancerClient.execute(null,null);
            roundRobinLoadBalancer.choose(null);
        }



        try{
            result.put(Providers.CLIENT_B,clientB.healthCheck());
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return JSON.toJSONString(result);
    }
}
