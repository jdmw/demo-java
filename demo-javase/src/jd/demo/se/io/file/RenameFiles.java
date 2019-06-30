package jd.demo.se.io.file;

import java.io.File;

import jd.util.io.file.FileUt;

public class RenameFiles {

	public static void main(String[] args) {
		File base = new File("F:\\doc\\Documents\\My WebZIP Sites\\pop-fashion.com\\www.pop-fashion.com\\global");
		
		FileUt.listFiles(base,(file,name)->file.isDirectory() || (name.contains(".css~") || name.contains(".js~")) , f->{
			if(f.isFile() ) {
				String name = f.getAbsolutePath();
				int i = name.lastIndexOf("~");
				if(i>0) {
					name = name.substring(0, i);
					System.out.println(name);
					f.renameTo(new File(name));
				}
				
			}
		});

	}

}
