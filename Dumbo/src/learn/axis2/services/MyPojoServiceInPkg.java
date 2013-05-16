package learn.axis2.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace="http://sampe.pojo.in.pkg/MyPojoService2",name="MyPojoService2")
public class MyPojoServiceInPkg {
	
	@WebMethod(action="urn:subtract",operationName="doSubtract")
	public int subtract(@WebParam(partName="firstNumber")int a,@WebParam(partName="secondNumber")int b){
		return a-b;
	}
	
	@WebMethod(action="urn:getAddress",operationName="getAddress")
	public Address getAddress(){
		Address address = new Address();
		address.setAddress("25th Street");
		address.setName("GW");
		return address;
	}
}
