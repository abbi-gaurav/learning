package learn.nio.netty.http.client.impl;

import org.jboss.netty.channel.ChannelFuture;

public interface EventFinishedNotifier {
	public void notifyCallback(ChannelFuture future);
}
