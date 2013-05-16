package learn.nio.netty.server;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class SquareDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		/*int readableBytes = buffer.readableBytes();
		if(readableBytes >0){
			byte[] bytesRead = new byte[readableBytes];
			buffer.readBytes(bytesRead, 0, readableBytes);
			return new String(bytesRead);
		}*/
		return buffer.toString(Charset.defaultCharset());
	}

}
