package test.threads.testing;

import java.util.concurrent.Semaphore;

@SuppressWarnings("unchecked")
public class BoundBuffer<E> {
	private final Semaphore availableItems, availableSpaces;
	private final E[] items;
	private int putPosition,takePosition;
	
	public BoundBuffer(int capacity) {
		availableItems = new Semaphore(0);
		availableSpaces = new Semaphore(capacity);
		items = (E[])new Object[capacity];
	}
	
	public boolean isEmpty(){
		return availableItems.availablePermits() == 0;
	}
	
	public boolean isFull(){
		return availableSpaces.availablePermits() == 0;
	}
	
	public void put(E x) throws InterruptedException{
		availableSpaces.acquire();
		doInsert(x);
		availableItems.release();
	}
	
	public E take() throws InterruptedException{
		availableItems.acquire();
		E item = doExtract();
		availableSpaces.acquire();
		return item;
	}
	private E doExtract() {
		int i = takePosition;
		E x = items[i];
		items[i] = null;
		takePosition= (++i == items.length)?0:i;
		return x;
	}

	private void doInsert(E x) {
		int i = putPosition;
		items[i] = x;
		putPosition = (++i == items.length?0:i);
	}
}
