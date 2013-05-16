package learn.axis2.client.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.context.SessionContext;
import org.apache.axis2.description.Parameter;
import org.apache.axis2.description.TransportInDescription;
import org.apache.axis2.engine.AxisError;
import org.apache.axis2.transport.TransportListener;
import org.apache.axis2.transport.http.AxisServletListener;
import org.apache.axis2.transport.http.HTTPTransportUtils;

public class TCPServer implements Runnable, TransportListener {

	private ConfigurationContext configurationContext;
	private TransportInDescription transportInDescription;
	private String scheme;
	private int port;
	private ServerSocket serverSocket;
	private boolean started;

	@Override
	public void init(ConfigurationContext axisConf,
			TransportInDescription transprtIn) throws AxisFault {

		this.configurationContext = axisConf;
		this.transportInDescription = transprtIn;
		this.scheme = transportInDescription.getName();
		if (!"tcp".equalsIgnoreCase(scheme)) {
			throw new AxisFault(AxisServletListener.class.getName() + " can only be used for tcp");
		}
		Parameter param = transportInDescription.getParameter(PARAM_PORT);
		if (param != null) {
			try {
				this.port = Integer.parseInt((String) param.getValue());
			} catch (NumberFormatException ex) {
				throw new AxisFault("Invalid port number");
			}
		} else {
			this.port = -1;
		}

	}

	@Override
	public synchronized void start() throws AxisFault {
		try {
			serverSocket = new ServerSocket(this.port);
			started = true;
		} catch (IOException e) {
			throw new AxisFault("Not able to start TCP server["+e.getLocalizedMessage()+"]");
		}
		this.configurationContext.getThreadPool().execute(this);
	}

	@Override
	public synchronized void stop() throws AxisFault {
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new AxisError("TCP server close failed", e);
		}finally{
			started = false;
		}
	}

	@Override
	public EndpointReference getEPRForService(String serviceName, String ip)
	throws AxisFault {
		return getEPRsForService(serviceName, ip)[0];
	}

	@Override
	public EndpointReference[] getEPRsForService(String serviceName, String ip)
	throws AxisFault {
		if (port == -1) {
			throw new AxisFault("Port number for transport " + scheme + " has not yet been detected");
		}
		return HTTPTransportUtils.getEPRsForService(configurationContext, transportInDescription,
				serviceName, ip, port);
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

	@Override
	public void run() {
		while(started){
			try {
				Socket socket = serverSocket.accept();
				if(socket != null){
					//TODO: need to forward data somewhere
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
