package jd.demo.app.office.uno.convert;

import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;

import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.XCloseable;
import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.Bootstrap ;
import com.sun.star.comp.helper.BootstrapException;

import java.io.File;


public class DocumentConverter {
	/**
	 * Containing the loaded documents
	 */
	private static XComponentLoader xCompLoader;

	static {
		try {
			XComponentContext xContext = Bootstrap.bootstrap();
			System.out.println("Connected to a running office ...");

			// get the remote office service manager
			com.sun.star.lang.XMultiComponentFactory xMCF = xContext.getServiceManager();

			Object oDesktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xContext);

			xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class,oDesktop);
		} catch (BootstrapException|com.sun.star.uno.Exception e) {
			throw new RuntimeException(e);
		} 
			
	}

	public static void convert(File indent, String convertType, String extension, File outdir) {

		String sOutUrl = "file:///" + outdir.getAbsolutePath().replace('\\', '/');

		System.out.println("\nThe converted documents will stored in \"" + outdir.getPath() + "!");

		// Converting the document to the favoured type
		try {
			// Composing the URL by replacing all backslashes
			String sUrl = "file:///" + indent.getAbsolutePath().replace('\\', '/');

			// Loading the wanted document
			PropertyValue propertyValues[] = new PropertyValue[1];
			propertyValues[0] = new PropertyValue();
			propertyValues[0].Name = "Hidden";
			propertyValues[0].Value = Boolean.TRUE;

			Object oDocToStore = DocumentConverter.xCompLoader.loadComponentFromURL(sUrl, "_blank", 0,propertyValues);

			// Getting an object that will offer a simple way to store
			// a document to a URL.
			com.sun.star.frame.XStorable xStorable = (XStorable) UnoRuntime
					.queryInterface(com.sun.star.frame.XStorable.class, oDocToStore);

			// Preparing properties for converting the document
			propertyValues = new PropertyValue[2];
			// Setting the flag for overwriting
			propertyValues[0] = new PropertyValue();
			propertyValues[0].Name = "Overwrite";
			propertyValues[0].Value = Boolean.TRUE;
			// Setting the filter name
			propertyValues[1] = new PropertyValue();
			propertyValues[1].Name = "FilterName";
			propertyValues[1].Value = convertType;

			// Appending the favoured extension to the origin document name
			int index1 = sUrl.lastIndexOf('/');
			int index2 = sUrl.lastIndexOf('.');
			String sStoreUrl = sOutUrl + sUrl.substring(index1, index2 + 1) + extension;

			// Storing and converting the document
			xStorable.storeAsURL(sStoreUrl, propertyValues);

			// Closing the converted document. Use XCloseable.close if the
			// interface is supported, otherwise use XComponent.dispose
			com.sun.star.util.XCloseable xCloseable = (XCloseable) UnoRuntime
					.queryInterface(com.sun.star.util.XCloseable.class, xStorable);

			if (xCloseable != null) {
				xCloseable.close(false);
			} else {
				com.sun.star.lang.XComponent xComp = (XComponent) UnoRuntime
						.queryInterface(com.sun.star.lang.XComponent.class, xStorable);
				xComp.dispose();
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}


	}

	/**
	 * Bootstrap UNO, getting the remote component context, getting a new instance
	 * of the desktop (used interface XComponentLoader) and calling the static
	 * method traverse
	 * 
	 * @param args The array of the type String contains the directory, in which all
	 *             files should be converted, the favoured converting type and the
	 *             wanted extension
	 */
	public static void main(String args[]) {

		File file = new File("D:/1.docx");
		try {
			convert(file,"pdf","pdf",file.getParentFile());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
}

