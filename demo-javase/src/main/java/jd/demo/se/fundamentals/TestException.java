package jd.demo.se.fundamentals;

import java.io.InputStream;
import java.net.URL;

import jd.util.io.IOUt;

public class TestException {

	public static void main(String[] args) {
		try (InputStream is = new URL("https://www.ldoceonline.com/dictionary/wrap").openStream(); ){
			System.out.println(IOUt.toString(is));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
