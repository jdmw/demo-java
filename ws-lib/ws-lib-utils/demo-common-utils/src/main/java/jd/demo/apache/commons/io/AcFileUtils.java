package jd.demo.apache.commons.io;

import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;

public class AcFileUtils {

	public static void main(String[] args) throws MalformedURLException, IOException {
		// read file
		File file = new File("pom.xml").getAbsoluteFile();
		List<String> lines = FileUtils.readLines(file, "UTF-8");
		// lines.forEach(line->System.out.println(line));
		out.println(lines.get(0));
		
		File pomXmlfile = new File("src/../pom.xml") ;
		out.println(pomXmlfile.getAbsolutePath() + "->" + FilenameUtils.normalize(pomXmlfile.getAbsolutePath()));
		
		out.format("free space:%dkb\n", FileSystemUtils.freeSpaceKb());
		
		LineIterator li = FileUtils.lineIterator(pomXmlfile);
		try{
			li.forEachRemaining(line->out.println(line));
		}finally {
			LineIterator.closeQuietly(li);
		}
		
		
	}

}
