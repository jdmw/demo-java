package jd.demo.se.collections;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DemoMap {

    public static void fun(String...args) {
        
    }

	
	public static void main(String[] args) {
		Map<Integer,String> map = new HashMap<>();
        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "C");
        for(Integer i : map.keySet()) {
        	if("B".equals(map.get(i))) {
        		map.remove(i);
        	}
        }
        System.out.println(map);
        
        Map map1 = new LinkedHashMap();
		map1.put("z", -1);
		map1.put("a", -2);
	}

}
