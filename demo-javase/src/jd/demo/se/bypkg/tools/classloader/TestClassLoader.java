package jd.demo.se.bypkg.tools.classloader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import jd.demo.se.bypkg.tools.compiler.CharSequenceCompiler;
import org.xml.sax.SAXException;

import jd.util.io.IOUt;
import jd.util.io.file.FileUt;

public class TestClassLoader {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		
		String charset = "UTF-8" ;
		
		JdClassLoader classloader = new JdClassLoader("classes");
		Class<?> cf = Class.forName("jdmw.demo.compiler.example.Function1", true, classloader);
		System.out.println(cf.getClassLoader());
		System.out.println(classloader.getParent());
		
		String expression = "map.put(\"a\",1);";
		
		File sourceFile = FileUt.getFile(new File("classes"), "jdmw","demo","compiler","example","Function1.java.tpl");
		String pakcageName = "jdmw.demo.compiler.example";
		String className = "Function1Impl1" ;
		String qualifiedClassName = pakcageName + "." + className ;
		String content = new String(IOUt.toByteArray(sourceFile),charset)
			.replace("${packageName}", "jdmw.demo.compiler.example")
			.replace("${imports}", "")
			.replace("${classname}", className)
			.replace("${expression}", expression);
		Map<String,CharSequence> classes = new HashMap<>();
		classes.put(qualifiedClassName, content);

		CharSequenceCompiler compiler = new CharSequenceCompiler(classloader,null);
		Map<String,Class<?>> compiled = compiler.compile(classes);
		
		Class<?> clazz = Class.forName(qualifiedClassName, true, classloader);
		System.out.println(clazz.getClassLoader());
		
		Object obj = clazz.newInstance();
		Map<?, ?> map = new HashMap<Object, Object>();
		clazz.getMethod("doWithMap", Map.class).invoke(obj,map);
		System.out.println(map.get("a"));
	}
}