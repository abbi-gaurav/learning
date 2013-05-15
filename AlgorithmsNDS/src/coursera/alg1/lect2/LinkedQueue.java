package coursera.alg1.lect2;

import java.util.Iterator;

public class LinkedQueue<T> implements Queue<T>, Iterable<T>{
	private Node first;
	private Node last;
	private int size;
	
	private class Node{
		T item;
		Node next;
	}
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void enqueue(T item) {
		Node oldLast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		if(isEmpty()){
			first = last;
		}else{
			oldLast.next = last;
		}
		size++;
	}

	@Override
	public T dequeue() {
		if(isEmpty()){
			last = null;
			return null;
		}
		
		T item = first.item;
		first = first.next;
		size--;
		return item;
	}

	@Override
	public Iterator<T> iterator() {
		return new QueueIterator();
	}
	
	private final class QueueIterator implements Iterator<T>{
		
		private Node current;

		public QueueIterator() {
			this.current = first;
		}
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			
			if(!hasNext()){
				throw new IllegalStateException();
			}
			
			T item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	public int size() {
		return size;
	}
}
