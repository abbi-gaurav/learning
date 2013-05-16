package http.client.sample.usage;

import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

import learn.nio.netty.CommonUtils;
import learn.nio.netty.http.client.ConnectionInfo;
import learn.nio.netty.http.client.DataTransporter;
import learn.nio.netty.http.client.HttpClient;
import learn.nio.netty.http.client.RequestHeaders;
import learn.nio.netty.http.client.RequestType;
import learn.nio.netty.http.client.callbacks.ConnectionEventCallback;
import learn.nio.netty.http.client.callbacks.WriteCallback;
import learn.nio.netty.http.client.impl.HttpClientImpl;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClienTest {
	private static final int WAIT_TIME = 240;
	private static final String FILE_NAME = "fileName";
	private static final String URI = "/jettyServer/testHttpOutbound/";
	public static final Logger LOGGER = LoggerFactory.getLogger(HttpClienTest.class);
	private static final HttpClient httpClient = new HttpClientImpl(false);
	private static final ConnectionInfo connectionInfo = new ConnectionInfo("blrgislin01.sciblr.in.ibm.com", 25000, "9.113.231.79");
	
	@Test
	public void testPost() throws InterruptedException {
		DataTransporter dataTransporter = connect();
		sendHeaders(dataTransporter, RequestType.POST, TestUtils.addFileId(URI));
		sendData(dataTransporter,FILE_NAME);
		close(dataTransporter);
	}

	private void sendData(DataTransporter dataTransporter, String fileName) throws InterruptedException {
		ChannelBuffer buffer = ChannelBuffers.copiedBuffer(ByteOrder.BIG_ENDIAN,"SomeBuffer".getBytes());
		CallbackNotifier notifier = new CallbackNotifier();
		dataTransporter.writeBody(buffer, new WriteCallbackImpl(notifier));
		
		verifyEvent(notifier, "Body Not Sent", WAIT_TIME);
		
		
		//mark end of body
		CallbackNotifier notifierFinished = new CallbackNotifier();
		dataTransporter.writeBody(ChannelBuffers.EMPTY_BUFFER, new WriteCallbackImpl(notifierFinished));
		verifyEvent(notifier, "Body Finished", WAIT_TIME);
	}

	private void sendHeaders(DataTransporter dataTransporter, RequestType requestType, String uri) throws InterruptedException {
		final CallbackNotifier notifier = new CallbackNotifier();
		dataTransporter.writeHeaders(new RequestHeaders(requestType, uri, null,0), new WriteCallbackImpl(notifier));
		
		verifyEvent(notifier, "Connection Not Closed", WAIT_TIME);
	}

	private void close(DataTransporter dataTransporter) throws InterruptedException {
		CallbackNotifier notifier = new CallbackNotifier();
		dataTransporter.close(new ConnectEventCallbackImpl(notifier));
		verifyEvent(notifier, "Connection Not Closed", WAIT_TIME);
	}

	private static DataTransporter connect() throws InterruptedException {
		CallbackNotifier notifier = new CallbackNotifier();
		DataTransporter dataTransporter = httpClient.connect(connectionInfo, new ConnectEventCallbackImpl(notifier));
		verifyEvent(notifier, "Connection Not succeeded", WAIT_TIME);
		return dataTransporter;
	}

	private static void verifyEvent(final CallbackNotifier notifier, String message, int waitTime)
			throws InterruptedException {
		junit.framework.Assert.assertTrue(message, notifier.await(waitTime, TimeUnit.SECONDS));
	}
	
	
	private static final class WriteCallbackImpl implements WriteCallback {
		private final CallbackNotifier notifier;

		private WriteCallbackImpl(CallbackNotifier notifier) {
			this.notifier = notifier;
		}

		@Override
		public void failure(Throwable cause) {
			LOGGER.error("Headers Sent failed", cause);
		}

		@Override
		public void canWriteData() {
			LOGGER.debug("Headers Send Event succeded {}", CommonUtils.getLoggableArgs(connectionInfo));
			notifier.notifiyWaiter();
		}
	}


	private static final class ConnectEventCallbackImpl implements ConnectionEventCallback {
		private CallbackNotifier notifier;
		private ConnectEventCallbackImpl(CallbackNotifier notifier) {
			this.notifier = notifier;
		}
		@Override
		public void failure(Throwable cause) {
			LOGGER.error("Connection failed", cause);
		}
		@Override
		public void connectionEventSuccess() {
			LOGGER.debug("Connection Event succeded {}", CommonUtils.getLoggableArgs(connectionInfo));
			notifier.notifiyWaiter();
		}
	}
}
