package jd.demo.se.collections;

import java.util.Hashtable;

public class DemoHashtable {

	public static void main(String[] args) {
		Hashtable<Float,String> map = new Hashtable<>();
		map.put(1f, "1.0");
		map.put(0.8f, "0.8");
		map.put(0.9f, "0.9");
		System.out.println(map);
		System.out.println(map.elements().nextElement());
		System.out.println(map.elements().nextElement());
	}

}
