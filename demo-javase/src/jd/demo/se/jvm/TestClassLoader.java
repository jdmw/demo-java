package jd.demo.se.jvm;


public class TestClassLoader {
	public static Class<?> test() throws ClassNotFoundException{
		System.out.println("classloader of jd-util:");
		ClassLoader cl = TestClassLoader.class.getClassLoader();
		do{
			System.out.println("classloader:"+cl);
		}while((cl =cl.getParent())!=null);
		return TestClassLoader.class.getClassLoader().getParent()
				.loadClass("com.cfets.dqs.u.shibor.ShiborHelper");
	}
	
	public static ClassLoader getClassLoader(){
		return TestClassLoader.class.getClassLoader();
	}
}
