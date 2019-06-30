package jd.demo.springboot.hello.aop;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



@ControllerAdvice
class ExceptionHandlerAdvice{
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Map<String,String> defaultExceptionHandler(Exception e){
		Map<String,String> map = new HashMap<>();
		map.put("responseCode", "1");
		map.put("responseMsg", e.getMessage());
		return map ;
	}
}