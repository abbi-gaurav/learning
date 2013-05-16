package test.simple.webservice.samples;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.WSDL20ToAllAxisServicesBuilder;

public class AddNewRuntimeWebService {
	public String addService(String name) throws FileNotFoundException, AxisFault{
		WSDL20ToAllAxisServicesBuilder builder = new WSDL20ToAllAxisServicesBuilder
						(new FileInputStream("/home/gauravabbi/official/development/rubik/wsdl2service/SampleService.wsdl"));
		AxisService service = builder.populateAllServices().get(0);
		String newServiceName = "RuntimeAdded_"+service.getName();
		service.setName(newServiceName);
		MessageContext messageContext = MessageContext.getCurrentMessageContext();
		ConfigurationContext configurationContext = messageContext.getConfigurationContext();
		configurationContext.deployService(service);
		String result = "Service Added successfully";
		System.out.println(result);
		return result;
	}
}
