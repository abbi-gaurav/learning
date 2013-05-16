package learn.nio.netty.http.client;

import java.util.Map;

public class RequestHeaders {
	private final RequestType type;
	private final String uri;
	private final Map<String, String> httpHeaders;
	private final int contentLength;

	public RequestHeaders(RequestType type, String uri, Map<String, String> httpHeaders, int contentLength) {
		this.type = type;
		this.uri = uri;
		this.httpHeaders = httpHeaders;
		this.contentLength = contentLength;
	}

	public RequestType getType() {
		return type;
	}

	public String getUri() {
		return uri;
	}

	public Map<String, String> getHttpHeaders() {
		return httpHeaders;
	}

	public int getContentLength() {
		return contentLength;
	}
}