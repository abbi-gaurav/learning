package learn.nio.netty.http.client.impl;


import learn.nio.netty.http.client.DataTransporter;
import learn.nio.netty.http.client.RequestHeaders;
import learn.nio.netty.http.client.callbacks.ConnectionEventCallback;
import learn.nio.netty.http.client.callbacks.WriteCallback;
import learn.nio.netty.utils.Constants;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.handler.codec.http.DefaultHttpChunk;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;

public class DataTransporterImpl implements DataTransporter {

	private static final class WriteEventFinishedNotifier implements EventFinishedNotifier {
		private final WriteCallback callback;

		private WriteEventFinishedNotifier(WriteCallback callback) {
			this.callback = callback;
		}

		@Override
		public void notifyCallback(ChannelFuture future){
			if(future.isSuccess()){
				callback.canWriteData();
			}else{
				Utils.notifyFailure(callback, future);
			}
		}
	}

	private Channel channel;

	public DataTransporterImpl(Channel channel) {
		this.channel = channel;
	}
	
	@Override
	public void writeHeaders(RequestHeaders requestHeaders, final WriteCallback callback) {
		HttpMethod httpMethod = Utils.getHttpMethod(requestHeaders.getType());
		HttpRequest request = new DefaultHttpRequest(Constants.HTTP11,httpMethod, requestHeaders.getUri());
		Utils.addRequestHeaders(request,requestHeaders);
		
		doWrite(request, callback);
	}
	
	@Override
	public void writeBody(ChannelBuffer buffer,WriteCallback callback){
		HttpChunk chunk = new DefaultHttpChunk(buffer);
		doWrite(chunk, callback);
	}

	private void doWrite(Object message, WriteCallback callback) {
		ChannelFuture future = channel.write(message);
		Utils.handleEventCallback(future,new WriteEventFinishedNotifier(callback));
	}
	
	@Override
	public void close(ConnectionEventCallback callback) {
		ChannelFuture future = channel.close();
		Utils.handleConnectionFuture(callback,future);
	}
	
	@Override
	public String toString() {
		return channel.toString();
	}

}
