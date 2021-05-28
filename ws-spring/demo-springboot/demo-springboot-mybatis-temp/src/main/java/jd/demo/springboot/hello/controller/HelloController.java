package jd.demo.springboot.hello.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

	@RequestMapping("hello")
	public String hello() {
		return "Hello,Spring boot!";
	}

	/**
	 * 获取简历数据
	 * @param request
	 * @return
	 */
	@GetMapping("/v1/profile")
	public String getProfile(HttpServletRequest request){
		return "success" ;
	}

	@RequestMapping(value = "/v1/profile/{part}/by/id", method = RequestMethod.GET)
	public String getResourceById(HttpServletRequest request,@PathVariable("part") String part,@RequestParam("id") Integer id) throws Exception {
		System.out.println(id);
		return "success" ;
	}

	@RequestMapping(value = "/wx/profile/{part}", method = {RequestMethod.POST,RequestMethod.PUT})
	public String post(HttpServletRequest request,@PathVariable("part") String part,@RequestBody String data) throws Exception {
		System.out.println(data);
		return data ;
	}

	@RequestMapping(value = "/v1/profile/{part}", method = RequestMethod.DELETE)
	public String getResources(HttpServletRequest request,@PathVariable("part") String part,@RequestParam("id") Integer id) throws Exception {
		System.out.println(id);
		return "success" ;
	}

}
