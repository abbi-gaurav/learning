package learn.nio.netty.http.client.impl;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpClientCodec;

public class HttpClientPipelineFactory implements ChannelPipelineFactory {

	private final boolean ssl;

	public HttpClientPipelineFactory(boolean isSSL) {
		this.ssl = isSSL;
	}
	
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		//TODO: add ssl later
		
		pipeline.addLast("codec", new HttpClientCodec());
		pipeline.addLast("handler", new HttpReceiveHandler());
		return pipeline;
	}

}
