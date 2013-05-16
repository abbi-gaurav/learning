package learn.nio.netty.http.client.callbacks;


public interface Callback {

	public abstract void failure(Throwable cause);

}