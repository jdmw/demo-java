package jd.designpattern.dict.topdesign.structure.microkernel.pluginmgr;

import java.util.HashMap;
import java.util.Map;

public class PluginMgr {

	private final Map<String,Plugin<?>> plugins = new HashMap<>();
	
	public <T> Plugin<T> registerPlugin(Class<T> pluginIntefaceClass){
		String pluginId = pluginIntefaceClass.getSimpleName();
		pluginId = pluginId.substring(0,1).toLowerCase() + pluginId.substring(1);
		return registerPlugin(pluginIntefaceClass,pluginId);
	}
	
	public <T> Plugin<T> registerPlugin(String pluginId){
		return registerPlugin(null,pluginId);
	}
	
	public <T> Plugin<T> registerPlugin(Class<T> pluginIntefaceClass,String pluginId){
		if(!plugins.containsKey(pluginId)){
			Plugin<T> plugin =  new Plugin<T>(pluginIntefaceClass,pluginId);
			plugins.put(pluginId, plugin);
			return plugin;
		}else{
			throw new RuntimeException("plugin " + pluginId + " already exists.");
		}
	}
	
	public <T> PluginApi getPlugin(String pluginId){
		Plugin<T> plugin = (Plugin<T>) plugins.get(pluginId);
		if(plugin != null && !plugin.getPlugins().isEmpty()){
			return new PluginApiImpl(plugin.getPlugins());
		}
		return null;
	}
	
	
}
