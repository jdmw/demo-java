package jd.demo.app.office.uno.convert;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import org.jodconverter.JodConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;


public class UnoConnector {

	static int PORT = 8100 ;
	static String OPEN_OFFICE_COMMAND = "cmd /c soffice -headless -accept=\"socket,host=127.0.0.1,port="+PORT+";urp;\" -nofirststartwizard";
	
	static {
		try {
			Runtime.getRuntime().exec(OPEN_OFFICE_COMMAND);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
	
	/*
	static  void execute(Consumer<OpenOfficeConnection> consumer) {
		Process pro = null ;
		try {
			pro = Runtime.getRuntime().exec(OPEN_OFFICE_COMMAND);
	        // connect to an OpenOffice.org instance running on port 8100  
	        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", PORT);  
	        connection.connect();  

	        //OfficeManager officeManager = new DefaultOfficeManagerConfiguration().buildOfficeManager();
			//officeManager.start();
			
	        consumer.accept(connection);

			//officeManager.stop();

	        // close the connection  
	        connection.disconnect();  
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			pro.destroy();  
		}
		
	}

*/
	public static void main(String[] args) throws OfficeException {
		File inputFile = new File("D:/1.docx");
		File outputFile = new File("D:/1.pdf");
		/*execute((connection)->{

			
			OpenOfficeDocumentConverter converter = new OpenOfficeDocumentConverter(connection);  
			converter.convert(inputFile, outputFile);  
			        
		});*/
		
		// Create an office manager using the default configuration.
		// The default port is 2002. Note that when an office manager
		// is installed, it will be the one used by default when
		// a converter is created.
		final LocalOfficeManager officeManager = LocalOfficeManager.install(); 
		try {

		    // Start an office process and connect to the started instance (on port 2002).
		    officeManager.start();

		    // Convert
		    JodConverter
		             .convert(inputFile)
		             .to(outputFile)
		             .execute();
		} finally {
		    // Stop the office process
			officeManager.stop();
		    //OfficeUtils.stopQuietly(officeManager);
		}
	}

}
