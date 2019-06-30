package jd.demo.hadoop.hdfs;

import static jd.util.lang.Console.print;

import java.io.IOException;
import java.io.InputStream;

import jd.util.io.IOUt;

public class TestRead {

	public static void main(String[] args) {
		InputStream is = null;
		try {
			is = HdfsUrl.path("/input/slaves").openStream();
			print(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUt.close(is);
		}
	}
	
	

}
