package learn.dynamicClassLoading;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import learn.jms.MyLogger;

import org.xml.sax.SAXException;


public class MySchemaResolver extends Abstracthelper implements Helpable<Schema>{
	
	public static final String SCHEMA_NAME = "BAPI_CUSTOMER_GETLIST.xsd";

	public MySchemaResolver(String jarPaths) {
		super(jarPaths);
	}

	public Schema test(){
		//here a get a schema using my classloader
		Schema schema = null;
		classLoader.setSchemaName(SCHEMA_NAME);
		setCL();
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			schema = factory.newSchema(Thread.currentThread().
								getContextClassLoader().getResource(SCHEMA_NAME));
		} catch (SAXException e) {
			MyLogger.LOGGER.error("Error while finding schema",e);
		}
		unsetCL();
		return schema;
			
	}
}
