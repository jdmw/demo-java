package jd.demo.apache.commons.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

public class AcIOUtilsDemo {

	public static void main(String[] args) throws MalformedURLException, IOException {
		
		// read stream from net
		InputStream in = new URL("http://www.baidu.com").openStream();
		try {
			System.out.println(IOUtils.toString(in,Charset.defaultCharset()));
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(in);
		}
	}

}
