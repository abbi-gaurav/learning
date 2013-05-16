import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private Node first;
	private Node last;
	private int size;

	private class Node {
		Item item;
		Node next;
		Node previous;
	}

	public void addFirst(Item item) {
		throwIfNull(item);
		Node oldFirst = this.first;

		first = new Node();
		first.item = item;
		if (size == 0) {
			last = first;
		} else {

			first.next = oldFirst;
			oldFirst.previous = first;
		}

		size++;
	}

	public void addLast(Item item) {
		throwIfNull(item);
		Node oldLast = last;

		last = new Node();
		last.item = item;
		if (size == 0) {
			first = last;
		} else {
			last.previous = oldLast;
			oldLast.next = last;
		}

		size++;
	}

	public Item removeFirst() {
		throwIfEmpty();
		Item item = first.item;
		size--;

		if (size == 0) {
			first = last = null;
		} else {
			first = first.next;
			first.previous = null;
		}
		return item;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public Item removeLast() {
		throwIfEmpty();

		Item item = last.item;
		size--;

		if (size == 0) {
			first = last = null;
		} else {
			last = last.previous;
			last.next = null;
		}
		return item;
	}

	private void throwIfNull(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
	}

	private void throwIfEmpty() {
		if (size <= 0) {
			throw new NoSuchElementException();
		}
	}

	private class DequeIterator implements Iterator<Item> {
		private Node current = first;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			Item item = current.item;

			current = current.next;

			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Not supported");
		}

	}

	@Override
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

}
