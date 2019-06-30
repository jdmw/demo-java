package jd.demo.se.collections.src;

import java.util.Collection;
import java.util.HashMap;

public class SrcHashMap {

	public static void main(String[] args) {
		HashMap<String,String> map = new HashMap<>();
		map.put("a", "1");
		map.put("ab", "1");
		map.put("abc", "1");
		map.put("bc", "1");
		map.put("cd", "1");
		map.put("ef", "1");
		String value = map.get("a");
		map.put("a", "2");
		int size = map.size();
		map.remove("a");
		Collection<String> values = map.values();
	}

}
