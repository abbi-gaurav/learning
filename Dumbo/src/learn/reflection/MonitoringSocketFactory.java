package learn.reflection;

import java.net.SocketImpl;
import java.net.SocketImplFactory;

public class MonitoringSocketFactory
implements SocketImplFactory {
	public SocketImpl createSocketImpl() {
		try {
			return new MonitoringSocketImpl();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
