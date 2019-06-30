package jd.designpattern.dict.topdesign.structure.microkernel.pluginmgr;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jd.util.ReflectUtil;

public interface PluginApi {
	public <T> T invoke(String method,Object ... args);
}

class PluginApiImpl implements PluginApi{

	private final List<?> plugins ;

	public PluginApiImpl(List<?> plugins) {
		this.plugins = plugins;
	}
	
	public <T> T invoke(String method,Object ... args){
		Object rst = null;
		for(Object obj : plugins){
			Class<?>[] pclas = new Class<?>[args.length];
			int i=0;
			do{
				pclas[i] = args[i].getClass();
			}while(++i<args.length);
			
			Method m= null;
			try {
				m = obj.getClass().getMethod(method, pclas);
				rst = m.invoke(obj, args);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(rst != null){
				break;
			}
		}
		return rst!=null?(T)rst:null ;
	}
}


