package learn.nio.listener;

import java.nio.ByteBuffer;

public class ClientHandlerImpl implements ClientHandler{

	@Override
	public void handleInput(ChannelFacade facade) {
		ByteBuffer buffer;
		while((buffer = facade.getInputQueue().poll()) != null){
			byte[] bytes = new byte[buffer.remaining()];
			buffer.get(bytes, 0, bytes.length);
			System.out.println(new String(bytes));
			buffer.clear();
		}
		
	}

}
