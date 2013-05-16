package learn.axis2.client.listener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.AxisServlet;

public class HttpListenerServlet extends AxisServlet{

	private static final long serialVersionUID = 8766026769503504219L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		System.out.println("after parent do get");
	}
	
	protected void initTransports() throws AxisFault {
		// TODO not sure what to do here
	}
}
