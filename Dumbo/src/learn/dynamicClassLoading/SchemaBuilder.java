package learn.dynamicClassLoading;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import learn.jms.MyLogger;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SchemaBuilder {
	private final String OpDirectory;

	public SchemaBuilder(String opDirectory) {
		OpDirectory = opDirectory;
	}

	public URL createSchema(String name) {
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			factory.setNamespaceAware(true);

			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new DefaultHandler());
			Document doc = builder.parse(getSchemaAsStream());
			
			return addToRepository(doc,name);
		}catch (ParserConfigurationException e) {
			MyLogger.LOGGER.error("Error in parsing", e);
		} catch (FileNotFoundException e) {
			MyLogger.LOGGER.error("File does not exist", e);
		} catch (SAXException e) {
			MyLogger.LOGGER.error("Error in parsing", e);
		} catch (IOException e) {
			MyLogger.LOGGER.error("IO error", e);
		} catch (TransformerConfigurationException e) {
			MyLogger.LOGGER.error("Error in adding to repository",e);
		} catch (TransformerException e) {
			MyLogger.LOGGER.error("Error in adding to repository",e);
		}
		throw new RuntimeException("Schema Creation failed");
	}

	private URL addToRepository(Document doc,String name) 
	throws FileNotFoundException, TransformerException, MalformedURLException {
		
		try{
			DOMSource source = new DOMSource(doc);

			TransformerFactory trfFactory = TransformerFactory.newInstance();
			Transformer transformer = trfFactory.newTransformer();
			String schemaURL = this.OpDirectory+name;
			StreamResult result = new StreamResult(new FileOutputStream(schemaURL));
			transformer.transform(source, result);
			URL url = new File(schemaURL).toURI().toURL();
			return url;
		}finally{
			//TODO: might need to close streams
		}
	}
	
	private InputStream getSchemaAsStream() throws FileNotFoundException {
		return new FileInputStream("/home/gauravabbi/learning/dcl/BAPI_CUSTOMER_GETLIST.xsd");
	}
}
