package threading.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import learn.nio.listener.NIOServerDispatcher;

public final class Pool<T> {
	private final BlockingQueue<T> pool;
	public Pool(Collection<? extends T> objects) {
		pool = new ArrayBlockingQueue<T>(objects.size(), false, objects);
	}
	
	public T borrow() throws InterruptedException{
		return pool.take();
	}
	
	public void giveBack(T poolItem) throws InterruptedException{
		pool.put(poolItem);
	}

	public static <I> Pool<I> configure(int size,PoolItemCreator<I> creator) {
		List<I> list = new ArrayList<I>();
		for(int i = 0; i< NIOServerDispatcher.POOL_SIZE; i++){
			list.add(creator.create());
		}
		
		return new Pool<I>(list);
	}
}
