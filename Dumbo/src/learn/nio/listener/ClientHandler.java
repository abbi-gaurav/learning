package learn.nio.listener;

public interface ClientHandler {
	public void handleInput(ChannelFacade facade);
}
