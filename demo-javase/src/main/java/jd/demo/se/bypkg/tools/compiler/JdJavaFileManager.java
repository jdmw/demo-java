package jd.demo.se.bypkg.tools.compiler;

import jd.demo.se.bypkg.tools.classloader.JdClassLoader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;


public class JdJavaFileManager<M extends JavaFileManager> extends ForwardingJavaFileManager<JavaFileManager> {

	private Map<URI,JavaFileObject> files = new TreeMap<URI,JavaFileObject>();
	
	private JdClassLoader classloder ;
	
	protected JdJavaFileManager(JavaFileManager fileManager,JdClassLoader classloder) {
		super(fileManager);
		this.classloder = classloder;
	}

	protected void putFileForInput(Location location,String packageName,String relativeName,JavaFileObject object){
		files.put(toURI(location,packageName,relativeName), object);
	}
	
	@Override
	public FileObject getFileForInput(Location location,String packageName,String relativeName) throws IOException{
		FileObject file = files.get(toURI(location,packageName,relativeName));
		if(file == null){
			file = super.getFileForInput(location, packageName, relativeName);
		}
		return file ;
	}
	
	@Override
	public JavaFileObject getJavaFileForOutput(Location location,
            String qualifiedName,Kind kind, FileObject outputFile) throws IOException{
		JavaFileObject file = new JdJavaFileObject(qualifiedName,kind);
		int dotPos = qualifiedName.lastIndexOf(".");
		String packageName = dotPos == -1 ? "" : qualifiedName.substring(0,dotPos);
		String classname = dotPos == -1 ? qualifiedName : qualifiedName.substring(dotPos+1);
		URI uri = toURI(StandardLocation.CLASS_PATH,packageName,classname);
		files.put(uri, file);
		this.classloder.addJavaFileObject(qualifiedName, file);
		return file;
	}
	
	@Override
	public String inferBinaryName(Location location, JavaFileObject file) {
        if(file instanceof JdJavaFileManager){
        	return file.getName();
        }
		return fileManager.inferBinaryName(location, file);
    }
	
	/**
     * Lists all file objects matching the given criteria in the given
     * location.  List file objects in "subpackages" if recurse is
     * true.
     *
     * @param location     a location
     * @param packageName  a package name
     * @param kinds        return objects only of these kinds
     * @param recurse      if true include "subpackages"
     * @return an Iterable of file objects matching the given criteria
	 * @throws IOException 
     */
	@Override
	public Iterable<JavaFileObject> list(Location location, String packageName,
    		Set<Kind> kinds,boolean recurse) throws IOException{
		List<JavaFileObject> list = new ArrayList<JavaFileObject>(files.size());
    	if(location == StandardLocation.SOURCE_PATH && kinds.contains(Kind.SOURCE)){
    		for(JavaFileObject file : files.values()){
    			if(file.getKind() == Kind.SOURCE && file.getName().startsWith(packageName)){
    				list.add(file);
    			}
    		}
    	}else if(location == StandardLocation.CLASS_PATH && kinds.contains(Kind.CLASS)){
    		for(JavaFileObject file : files.values()){
    			if(file.getKind() == Kind.CLASS && file.getName().startsWith(packageName)){
    				list.add(file);
    			}
    		}
    	}
    	Iterator<JavaFileObject> si = super.list(location, packageName, kinds, recurse).iterator();
    	while(si.hasNext()){
    		list.add(si.next());
    	}
    	return list;
    }
                                  
	private URI toURI(Location location,String packageName,String relativeName){
		try {
			return new URI(location.getName() + "/" + packageName.replace('.', '/') + "/" + relativeName);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}


	public JdClassLoader getClassloder() {
		return classloder;
	}
}
