package jd.demo.se.bypkg.lang.reflect;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class ReadFileInJar {

	public static void main(String[] args) throws IOException {
		Enumeration<URL> res = ReadFileInJar.class.getClassLoader().getResources("META-INF/services/test");
		while(res.hasMoreElements()) {
			System.out.println(res.nextElement());
		}
	}

}
