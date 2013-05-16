import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ServiceContext;
import org.apache.axis2.service.Lifecycle;


public class MyRequestScopeService implements Lifecycle{

	@Override
	public void init(ServiceContext context) throws AxisFault {
		System.out.println("Service started");
		
	}

	@Override
	public void destroy(ServiceContext context) {
		System.out.println("Service destroyed");
	}
	
	public int divide(int a,int b){
		return a/b;
	}

}
