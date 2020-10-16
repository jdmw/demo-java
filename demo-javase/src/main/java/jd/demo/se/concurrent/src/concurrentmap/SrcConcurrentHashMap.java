package jd.demo.se.concurrent.src.concurrentmap;

import java.util.concurrent.ConcurrentHashMap;

import jd.util.lang.Console;

public class SrcConcurrentHashMap {

	public static void demo() {
		ConcurrentHashMap<String,Integer> cmap = new ConcurrentHashMap<>();
		cmap.put("A", 1);
		Console.ln(cmap.get("A"));
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
