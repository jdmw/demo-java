package jd.demo.se.bypkg.lang;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import jd.util.io.IOUt;

public class DemoProcessBuilder {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ProcessBuilder b = new ProcessBuilder("java","-version");
		Map<String, String> env = b.environment();
		Map<String, String> sysEnv = System.getenv();
		sysEnv.forEach((k,v) -> System.out.println("[System Env] " + k + " : " + v));
		env.putAll(sysEnv);
		b.directory(new File("."));
		Process p = b.start();
		InputStream is = p.getInputStream();
		IOUt.copy(is, System.out);
	}

}
