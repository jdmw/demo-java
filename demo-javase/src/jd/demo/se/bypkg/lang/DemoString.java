package jd.demo.se.bypkg.lang;

import jd.util.lang.Console;

public class DemoString {

	public static void main(String[] args) {
		Console.ln(" Bi:1".split(":")[0].trim());
		String protocol = "HTTP/1.1";
		protocol = protocol.split("/")[0].toLowerCase();
		System.out.println(protocol);
		String urlPattern = "/*".replace("*", ".*");
		System.out.println(urlPattern);
		System.out.println("/A/B".matches(urlPattern));

	}

}
