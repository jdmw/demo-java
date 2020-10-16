package jd.demo.se.bypkg.tools.compiler.example;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

import jd.demo.se.bypkg.tools.classloader.JdClassLoader;
import org.xml.sax.SAXException;

public class Parent {

public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		JdClassLoader classloader = new JdClassLoader("bin");
		Class<Parent> clazz = (Class<Parent>) Class.forName("jdmw.demo.compiler.example.Son", true, classloader) ;
		Parent son = clazz.newInstance();
		System.out.println(son);
	}
}