package jd.demo.se.bypkg.tools.compiler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.tools.SimpleJavaFileObject;


public class JdJavaFileObject extends SimpleJavaFileObject {

	private final CharSequence content ;
	private ByteArrayOutputStream bytecode ;
	
	public JdJavaFileObject(String qualifiedClassName, final CharSequence source) {
		super(toURI(qualifiedClassName), Kind.SOURCE);
		this.content = source ;
	}

	public JdJavaFileObject(String qualifiedClassName, Kind kind) {
		super(toURI(qualifiedClassName),kind);
		this.content = null;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors){
		if(this.content == null || this.content.length() == 0){
			throw new UnsupportedOperationException("not content found");
		}
		return this.content;
	}
	
	@Override
    public InputStream openInputStream() throws IOException {
        return new ByteArrayInputStream(bytecode.toByteArray());
    }

	@Override
    public OutputStream openOutputStream() throws IOException {
		bytecode = new ByteArrayOutputStream();
        return bytecode ;
    }
	private static URI toURI(String basename){
		try {
			return new URI(basename);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}
