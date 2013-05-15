package coursera.alg1.lect2;

@SuppressWarnings("unchecked")
public class ResizableArrayBasedQueue<T> implements Queue<T> {
	private T[] store;
	private int first;
	private int last;

	public ResizableArrayBasedQueue() {
		this.store = (T[])new Object[1];
		this.first = 0;
		this.last = 0;
	}

	@Override
	public boolean isEmpty() {
		return first == 0;
	}

	@Override
	public void enqueue(T item) {
		int currentQueueSize = currentQueueSize();
		if(last == store.length){
			resize(currentQueueSize*2);
		}
		this.store[last++] = item;
	}

	private void resize(int size) {
		T[] newStore = (T[]) new Object[size];
		for(int i = 0; i < last-first;i++){
			newStore[i] = store[i+first];
		}
		this.store = newStore;
		this.last = last - first;
		this.first = 0;
	}

	@Override
	public T dequeue() {
		if(first >= last){
			return null;
		}
		int currentQueueSize = currentQueueSize();
		if(currentQueueSize == store.length/4){
			resize(currentQueueSize/2); 
		}
		return store[first++];
	}

	private int currentQueueSize() {
		return last-first+1;
	}
}
