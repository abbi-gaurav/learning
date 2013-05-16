package learn.axis2;

import learn.axis2.services.MyPojoServiceInPkg;

import org.apache.axis2.AxisFault;
import org.apache.axis2.engine.AxisServer;

public class StartAxisAndDeployService {
	public static void main(String[] args) throws AxisFault {
		new AxisServer().deployService(MyPojoServiceInPkg.class.getName());
		
	}
}
