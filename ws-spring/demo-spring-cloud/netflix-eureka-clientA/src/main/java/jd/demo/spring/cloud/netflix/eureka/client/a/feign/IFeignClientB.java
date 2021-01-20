package jd.demo.spring.cloud.netflix.eureka.client.a.feign;

import jd.demo.spring.cloud.netflix.eureka.client.commom.constants.Providers;
import jd.demo.spring.cloud.netflix.eureka.client.commom.web.IBaseController;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by huangxia on 2008/1/20.
 */
@FeignClient(value= Providers.CLIENT_B)
public interface IFeignClientB extends IBaseController{
}
