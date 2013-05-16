package learn.nio.listener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class HandlerFutureTask extends FutureTask<HandlerAdapter> {

	private final HandlerAdapter adapter;
	private NIOServerDispatcher statusChangeListener;

	public HandlerFutureTask(NIOServerDispatcher asynchronousIOListener, HandlerAdapter adapter) {
		super(adapter);
		this.adapter = adapter;
		this.statusChangeListener = asynchronousIOListener;
	}
	
	@Override
	protected void done() {
		statusChangeListener.enqueueStatusChange(adapter);
		try{
			get();
		}catch (ExecutionException e) {
			adapter.die();
		} catch (InterruptedException e) {
			adapter.die();
		}
	}
}
