package learn.axis2.client.sender;

public interface HttpRequestContext {
	ConnectionProperties getConnection();
	String getUri();
	byte[] getPayload();
}
