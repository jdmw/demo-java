package jd.demo.springboot.hello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

	@RequestMapping("errpage")
	public String error() {
		throw new RuntimeException("this is a exception");
	}
	
}
