package threading.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import learn.Helper;

public class ExtensiblePool<T> {

	private final PoolItemCreator<T> creator;
	private final int min;
	private final int max;
	private final ArrayBlockingQueue<T> pool;
	private volatile int itemsCreated = 0;
	private final Semaphore lock = new Semaphore(1);;

	public ExtensiblePool(PoolItemCreator<T> creator, int min, int max) {
		this.creator = creator;
		this.min = min;
		this.max = max;
		this.pool = new ArrayBlockingQueue<T>(max);
		configureWithMin();
	}

	private void configureWithMin() {
		for (int i = 0; i < min; i++) {
			T item = creator.create();
			itemsCreated++;
			pool.add(item);
		}
	}

	public T borrow(long timeout) throws InterruptedException {
		try {
			lock.acquire();
			
			if (!pool.isEmpty()) {
				return pool.poll();
			}
			
			if (itemsCreated < max) {
				T item = creator.create();
				itemsCreated++;
				pool.add(item);
				return pool.poll();
			}
		} finally {
			lock.release();
		}
		
		T poll = pool.poll(timeout, TimeUnit.SECONDS);

		Helper.checkNullability(poll,
				"Timeout exceeded while waiting for the Item to be available from the pool");

		return poll;
	}

	public void giveBack(T item) {
		pool.add(item);
	}
}
