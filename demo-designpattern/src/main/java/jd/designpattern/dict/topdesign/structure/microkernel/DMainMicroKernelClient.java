package jd.designpattern.dict.topdesign.structure.microkernel;

import jd.designpattern.dict.topdesign.structure.microkernel.ext.bean.Food;
import jd.designpattern.dict.topdesign.structure.microkernel.ext.impl.HomeStock;
import jd.designpattern.dict.topdesign.structure.microkernel.ext.impl.Store;
import jd.designpattern.dict.topdesign.structure.microkernel.pluginmgr.PluginApi;
import jd.designpattern.dict.topdesign.structure.microkernel.pluginmgr.PluginMgr;


public class DMainMicroKernelClient {

	private static String STORE_PLUGIN_ID = "stock" ;
	private static String FOOD_BEEF = "beef" ;
	private static String FRUIT_APPLE = "apple";
	private static String FRUIT_MELON = "melon" ;
	
	
	public static void main(String[] args) {
		HomeStock home = new HomeStock();
		Store store = new Store();
		home.storeFood(FRUIT_APPLE, 2);
		home.storeFood(FRUIT_MELON, 1);
		store.addGoods(FRUIT_APPLE, 10, 4.0);
		store.addGoods(FRUIT_MELON, 10, 5.0);
		store.addGoods(FOOD_BEEF, 10, 20.0);
		
		PluginMgr pm = new PluginMgr();
		pm.registerPlugin(STORE_PLUGIN_ID)
			.addImplObjects(home,store);
		PluginApi pa = pm.getPlugin(STORE_PLUGIN_ID);
		Food melon = pa.invoke("get", FRUIT_MELON);
		Food beef = pa.invoke("get", FOOD_BEEF);
		melon = pa.invoke("get", FRUIT_MELON);
	}
}
