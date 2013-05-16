package learn.nio.netty.utils;

import org.jboss.netty.handler.codec.http.HttpVersion;

public class Constants {

	public static final String KEEP_ALIVE = "keepAlive";
	public static final String TCP_NO_DELAY = "tcpNoDelay";
	public static final String CHILD_PREFIX = "child.";
	public static final byte CR = "\r".getBytes()[0];
    public static final byte LF = "\n".getBytes()[0];
	public static final String HANDLER = "handler";
	public static final String ENCODER = "encoder";
	public static final String DECODER = "decoder";
	public static final String FRAMER = "framer";
	public static final HttpVersion HTTP11 = HttpVersion.HTTP_1_1;
}
