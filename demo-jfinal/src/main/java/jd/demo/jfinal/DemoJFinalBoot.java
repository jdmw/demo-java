package jd.demo.jfinal;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;

import jd.demo.jfinal.controller.GreetingController;

public class DemoJFinalBoot extends JFinalConfig{

	
	public static void main(String[] args) {
		UndertowServer.start(DemoJFinalBoot.class, 8111, true);

	}

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		
	}

	@Override
	public void configRoute(Routes me) {
		me.setBaseViewPath("/demo");
		me.add("/hello",GreetingController.class);
		
	}

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		
	}

}
