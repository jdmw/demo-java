package jd.demo.se.bypkg.tools.compiler;

import jd.demo.se.bypkg.tools.classloader.JdClassLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class CharSequenceCompiler {

	static final String JAVA_EXTENSION = ".JAVA";
	
	private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); 
	
	private final JdClassLoader classloarder  ;
	
	private final List<String> options = new ArrayList<String>();
	
	private DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
	
	private final JdJavaFileManager<?> javaFileMgr;

	public CharSequenceCompiler(JdClassLoader classloarder, List<String> options){
		this.classloarder = classloarder;
		javaFileMgr = new JdJavaFileManager(compiler.getStandardFileManager(diagnostics, null, null),classloarder);
		if(options != null){
			this.options.addAll(options);
		}
	}
	
	public synchronized Map<String,Class<?>> compile(final Map<String,CharSequence> classes) throws ClassNotFoundException {
		diagnostics = new DiagnosticCollector<JavaFileObject>();
		List<JavaFileObject> sources = new ArrayList<>();
		for(Entry<String,CharSequence> entry : classes.entrySet()){
			String qualifiedClassName = entry.getKey();
			CharSequence content = entry.getValue();
			if(content != null){
				int dotPos = qualifiedClassName.lastIndexOf(".");
				String className   = (dotPos != -1)?(qualifiedClassName.substring(dotPos+ 1)) :  qualifiedClassName;
				String packageName = (dotPos != -1)?(qualifiedClassName.substring(0,dotPos)) :  "";
				JdJavaFileObject file = new JdJavaFileObject(className,content);
				javaFileMgr.putFileForInput(StandardLocation.SOURCE_PATH, packageName, className + JAVA_EXTENSION, file);
				sources.add(file);
			}
		}
		
		final CompilationTask task = compiler.getTask(null, javaFileMgr, diagnostics, this.options, null, sources);
		if(Boolean.TRUE.equals(task.call())){
			Map<String,Class<?>> compiled = new HashMap<String,Class<?>>();
			for(String qualifiedClassName : classes.keySet()){
				//Class<?> newClass = classloarder.loadClass(qualifiedClassName);
				//compiled.put(qualifiedClassName,newClass);
			}
			return compiled ;
		}else{
			System.err.println("compile failed:"+diagnostics);
		}
		return null ;
	}
}
