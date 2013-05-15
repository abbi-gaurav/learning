package coursera.alg1.lect2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedStack<T> implements Stack<T>, Iterable<T> {
	private Node first = null;
	private class Node{
		T item;
		Node next;
	}
	
	@Override
	public boolean isEmpty(){
		return null == first;
	}

	@Override
	public void push(T item){
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.next = oldFirst;
	}

	@Override
	public T pop(){
		return isEmpty()?null:doPop();
	}
	
	@Override
	public T peek() {
		return isEmpty() ? null : first.item;
	}
	
	private T doPop() {
		T item = first.item;
		first = first.next;
		return item;
	}
	
	private class ListeIterator implements Iterator<T>{
		private Node current = first;
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			if(!hasNext()){
				throw new NoSuchElementException();
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
	@Override
	public Iterator<T> iterator() {
		return new ListeIterator();
	}
}
