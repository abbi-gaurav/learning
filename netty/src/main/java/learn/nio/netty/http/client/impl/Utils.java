package learn.nio.netty.http.client.impl;

import java.util.Map.Entry;

import learn.nio.netty.http.client.RequestHeaders;
import learn.nio.netty.http.client.RequestType;
import learn.nio.netty.http.client.callbacks.Callback;
import learn.nio.netty.http.client.callbacks.ConnectionEventCallback;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;

public class Utils {

	public static void notifyFailure(Callback callback, ChannelFuture future) {
		callback.failure(future.getCause());
	}

	public static void handleConnectionFuture(final ConnectionEventCallback callback, final ChannelFuture future) {
		handleEventCallback(future, new EventFinishedNotifier() {
			@Override
			public void notifyCallback(ChannelFuture future) {
				if(future.isSuccess()){
					callback.connectionEventSuccess();
				}else{
					notifyFailure(callback, future);
				}
			}
		});
	}
	
	public static HttpMethod getHttpMethod(RequestType type){
		switch (type) {
		case GET:
			return HttpMethod.GET;
		default:
		case POST:
			return HttpMethod.POST;
		}
	}

	public static void addRequestHeaders(HttpRequest request, RequestHeaders requestHeaders) {
		if (requestHeaders.getHttpHeaders() != null) {
			for (Entry<String, String> entry : requestHeaders.getHttpHeaders().entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}
		request.setChunked(requestHeaders.getContentLength() == 0);
	}

	public static void handleEventCallback(ChannelFuture future, final EventFinishedNotifier notifier) {
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				notifier.notifyCallback(future);
			}
		});
	}
}
