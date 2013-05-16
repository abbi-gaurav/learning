package learn.axis2.client;

import javax.xml.stream.FactoryConfigurationError;

public class TestTransportSession {
	public static void main(String[] args) throws FactoryConfigurationError, Exception {
		SoapSessionWSClient.sendWithClient(args, new SoapSessionWSClient(args[0], args[1]));
	}
}
