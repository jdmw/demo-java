package jd.demo.dubbo.common.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import jd.demo.dubbo.common.service.IHelloService;

@Service(timeout = 5000,interfaceClass = IHelloService.class)
//@Component
public class HelloServiceImpl implements IHelloService {
	
	public HelloServiceImpl(){
		System.out.println("Instantiating HelloServiceImpl class");
	}
	
	@Override
	public String sayHello(String name) {
        return "Hello " + name;
    }
}