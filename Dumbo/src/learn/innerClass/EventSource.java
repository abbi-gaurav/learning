package learn.innerClass;

import java.util.concurrent.atomic.AtomicReference;

public class EventSource extends Thread{
	//private final BlockingQueue<EventListener> listeners = new LinkedBlockingQueue<EventListener>();
	private final AtomicReference<EventListener> listeners = new AtomicReference<EventListener>();

	public void registerEventListener(EventListener eventListener) {
		//listeners.add(eventListener);
		listeners.set(eventListener);
	}

	@Override
	public void run() {
		while(true){
			EventListener listener = listeners.getAndSet(null);
			if (listener != null) {
				listener.onEvent(null);
			}
			//			try {
			//				listeners.take().onEvent(null);
			//			} catch (InterruptedException e) {
			//				break;
			//			}
		}
	}

}
