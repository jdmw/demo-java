package jd.demo.se.fundamentals.annotation;

import java.util.Arrays;
import java.util.List;

public class DemoApplyAn {

	@SafeVarargs // Not actually safe!
	static void m(List<String>... stringLists) {
		Object[] array = stringLists;
		List<Integer> tmpList = Arrays.asList(42);
		array[0] = tmpList; // Semantically invalid, but compiles without warnings
		String s = stringLists[0].get(0); // Oh no, ClassCastException at runtime!
	}
	

	public static void main(String[] args) {
		Object[] arr = args ;
		m(Arrays.asList("a","b"));
		System.out.println(arr);
	}
	
}
