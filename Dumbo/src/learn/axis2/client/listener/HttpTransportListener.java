package learn.axis2.client.listener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.context.SessionContext;
import org.apache.axis2.description.TransportInDescription;
import org.apache.axis2.transport.TransportListener;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletMapping;
import org.xml.sax.SAXException;

import com.test.server.JettyServerParameters;
import com.test.server.MyServer;

public class HttpTransportListener implements TransportListener {

	private final MyServer jettyServer;
	private EndpointReference[] eprs;

	public HttpTransportListener(int port) throws FileNotFoundException, SAXException, IOException, Exception {
		Map<String, Class<? extends HttpServlet>> map = new HashMap<String, Class<? extends HttpServlet>>();
		map.put("/service1", HttpListenerServlet.class);
		JettyServerParameters params = new JettyServerParameters(port, -1,"/jettyAsyncResponse", map, null, false);
		
		this.jettyServer = new MyServer(params);
		jettyServer.start();
		this.eprs = generateEPRefs();
	}

	@Override
	public void init(ConfigurationContext axisConf, TransportInDescription transprtIn) throws AxisFault {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() throws AxisFault {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() throws AxisFault {
		// TODO Auto-generated method stub

	}

	@Override
	public EndpointReference getEPRForService(String serviceName, String ip) throws AxisFault {
		return eprs[0];
	}

	private EndpointReference[] generateEPRefs() {
		Context context = jettyServer.getContext();
		String contextPath = context.getContextPath();
		
		ServletMapping[] mappings = context.getServletHandler().getServletMappings();
		EndpointReference[] eprs = new EndpointReference[mappings.length];
		
		for (int i = 0; i<mappings.length;i++) {
			String servletPath = mappings[i].getPathSpecs()[0];
			String path = "http://"+jettyServer.getIp()+":"+jettyServer.getPort()+contextPath+servletPath;
			eprs[i] = new EndpointReference(path);
			System.out.println("EPR is ["+eprs[i]+"]");
		}
		
		return eprs;
	}

	@Override
	public EndpointReference[] getEPRsForService(String serviceName, String ip) throws AxisFault {
		return eprs;
	}

	@Override
	public SessionContext getSessionContext(MessageContext messageContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
