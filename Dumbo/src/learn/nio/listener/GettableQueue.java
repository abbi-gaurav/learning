package learn.nio.listener;

import java.util.concurrent.ArrayBlockingQueue;

public class GettableQueue<E> {
	private final ArrayBlockingQueue<E> queue;
	
	public GettableQueue(int size) {
		this.queue = new ArrayBlockingQueue<E>(size,true);
	}
	
	//TODO: make it better
	public boolean notFull(){
		return queue.remainingCapacity() > 0;
	}
	
	public boolean notEmpty(){
		return !queue.isEmpty();
	}
	
	//TODO: check for null??
	public E poll(){
		return queue.poll();
	}
	
	void put(E item){
		this.queue.add(item);
	}
	
}
