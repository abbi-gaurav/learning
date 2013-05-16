package learn.nio.listener.usageExample;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import learn.nio.byteBuffer.ByteBufferPoolManager;
import learn.nio.listener.ChannelFacade;
import learn.nio.listener.ClientHandler;

public class ClientHandlerImpl implements ClientHandler{
	public static final Logger LOGGER = LoggerFactory.getLogger(ClientHandlerImpl.class);
	
	@Override
	public void handleInput(ChannelFacade facade) {
		ByteBuffer buffer;
		while((buffer = facade.getInputQueue().poll()) != null){
			try{
				int remaining = buffer.remaining();
				
				LOGGER.debug("Bytes in buffer", new Object[]{remaining});
				
				byte[] bytes = new byte[remaining];
				buffer.get(bytes, 0, bytes.length);
				System.out.println(new String(bytes));
			}finally{
				if(buffer != null){
					buffer.clear();
					ByteBufferPoolManager.get().giveBack(buffer);
				}
			}
		}
	}

}
