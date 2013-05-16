package learn.nio.netty.utils;

import learn.nio.netty.server.Configuration;
import learn.nio.netty.server.TimeServerHandler;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class Helper {

	public static final StringEncoder STRING_ENCODER = new StringEncoder();
	public static final StringDecoder STRING_DECODER = new StringDecoder();
	
	public static void configurePipeLine(ServerBootstrap bootstrap, final Configuration configuration) {
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast(Constants.FRAMER, new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
				pipeline.addLast(Constants.DECODER, Helper.STRING_DECODER);
				pipeline.addLast(Constants.ENCODER, Helper.STRING_ENCODER);
				pipeline.addLast(Constants.HANDLER, new TimeServerHandler(configuration.getEventWaitTimeout()));
				return pipeline;
			}
		});
		bootstrap.setOption(Constants.CHILD_PREFIX+Constants.TCP_NO_DELAY, true);
		bootstrap.setOption(Constants.CHILD_PREFIX+Constants.KEEP_ALIVE, true);
	}



}
