package jd.designpattern.dict.topdesign.structure.microkernel.pluginmgr;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jd.util.ReflectUtil;


public class Plugin<T>{
	private final Class<T> pluginIntefaceClass ;
	private final String pluginId ;
	
	private List<T> plugins = new ArrayList<>();
	
	protected Plugin(Class<T> pluginIntefaceClass, String pluginId) {
		this.pluginIntefaceClass = pluginIntefaceClass;
		this.pluginId = pluginId;
	}

	public String getPluginId() {
		return pluginId;
	}

	public Plugin<T> addImplClasses(Class<? extends T> ... implClasses){
		for(Class<?> impl : implClasses){
			try {
				plugins.add((T) impl.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return this;
	}
	
	public Plugin<T> addImplObjects(Object ... objs){
		for(Object obj : objs){
			plugins.add((T) obj);
		}
		return this;
	}

	protected List<T> getPlugins() {
		return plugins;
	}
	
	
	
}