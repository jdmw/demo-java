package jd.demo.se.bypkg.tools.classloader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.tools.JavaFileObject;

import jd.util.StrUt;
import jd.util.io.IOUt;
import jd.util.io.file.FileUt;

public class JdClassLoader extends ClassLoader{

	private Map<String,JavaFileObject> classeObjectMap = new TreeMap<String,JavaFileObject>();
	
	private File baseclassespath ;
	private File[] libs = null ;
	
	public JdClassLoader(){}
	
	public JdClassLoader(ClassLoader loader){
		super(loader);
	}
	
	public JdClassLoader(String classesBasePath){
		this.baseclassespath = new File(classesBasePath); 
	}
	
	public JdClassLoader(String classesBasePath,File ... jars){
		this.baseclassespath = new File(classesBasePath); 
		this.libs = jars ;
	}
	
	/**
	 * add name/javaFileObject map
	 * @param qualifiedClassName: class full name include package name and it's simple name
	 * 		e.g: "org.proj.service.DemoService"
	 * @param file: a OUTPUT kind of javaFileObject
	 */
	public void addJavaFileObject(String qualifiedClassName,JavaFileObject file){
		classeObjectMap.put(qualifiedClassName, file);
	}
	
	protected Class<?> findClass(String qualifiedClassName) throws ClassNotFoundException {
		byte[] content = null ;
		if(classeObjectMap.size() > 0 && classeObjectMap.containsKey(qualifiedClassName)){
			JavaFileObject file = classeObjectMap.get(qualifiedClassName);
			try {
				content = IOUt.readToByteArrayAndClose(file.openInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			content = readDataFromFiles(qualifiedClassName);
		}
		if(content != null){
			return this.defineClass(qualifiedClassName, content, 0, content.length);
		}else{
			throw new ClassNotFoundException("can't not find class : " + qualifiedClassName);
		}
    }
	
	/*
	@Override
	protected synchronized Class<?> loadClass(final String classname,final boolean resolve) throws ClassNotFoundException{
		return super.loadClass(classname,resolve);
	}
	*/
	@Override
	public InputStream getResourceAsStream(String name){
		if(name.endsWith(".class")){
			String qualifiedClassName = name.substring(0,name.length() - ".class".length()).replace('/', '.');
			if(classeObjectMap.size() > 0 && classeObjectMap.containsKey(qualifiedClassName)){
				JavaFileObject file = classeObjectMap.get(qualifiedClassName);
				try {
					return file.openInputStream();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				byte[] bytes = readDataFromFiles(qualifiedClassName);
				if(bytes != null){
					return new ByteArrayInputStream(bytes);
				}
			}
		}
		return super.getResourceAsStream(name);
	}
	
	
	private byte[] readDataFromFiles(String classname){
		if(StrUt.isEmpty(classname)){
			return null;
		}
		if(this.baseclassespath != null && this.baseclassespath.exists()){
			String filename = classname.replace('.', File.separatorChar) + ".class";
			File classFile = new File(baseclassespath,filename);
			if(classFile.exists()){
				return IOUt.toByteArray(classFile) ;
			}
		}
		if(this.libs != null && this.libs.length > 0){
			String entryName = classname.replace('.', '/') + ".class";
			for(File jar : this.libs){
				if(jar.exists()){
					InputStream is = null ;
					JarInputStream jis = null ;
					JarEntry entry = null ;
					try{
						is = new FileInputStream(jar);
						jis = new JarInputStream(is);
						while((entry = jis.getNextJarEntry()) != null){
							if(entry.getName().equals(entryName)){
								return IOUt.toByteArray(jis);
							}
						}
					}catch(IOException ioe){
						ioe.printStackTrace();
					}finally{
						try {
							if(jis != null) jis.close();
						} catch (IOException e) {
							//
						}
						try {
							if(is != null) is.close();
						} catch (IOException e) {
							//
						}
					}
					
				}
			}
		}
		return null;
	}
	

}
