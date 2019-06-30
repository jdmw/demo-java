package jd.demo.hadoop.hdfs;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;

public class HdfsUrl {

	public static final String HOST = "192.168.208.13" ;
	
	static {
		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
	}
	
	public static URL path(String path) {
		try {
			return new URL("hdfs://" + HOST + path);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
