import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueueLLBased<Item> implements RandomizedQueue<Item> {
	private int size;

	private Node last;

	private class Node {
		Item item;
		Node previous;
		Node next;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void enqueue(Item item) {
		throwIfNull(item);

		Node oldLast = last;

		last = new Node();
		last.item = item;

		if (size != 0) {
			last.previous = oldLast;
			oldLast.next = last;
		}
		size++;
	}

	@Override
	public Item dequeue() {
		throwIfEmpty();
		Node randomNodetoBeRemoved = getRandomNode(last);
		removeNode(randomNodetoBeRemoved);
		size--;
		return randomNodetoBeRemoved.item;
	}

	@Override
	public Item sample() {
		throwIfEmpty();
		return getRandomNode(last).item;
	}

	@Override
	public Iterator<Item> iterator() {
		return new RQLLIterator();
	}

	@SuppressWarnings("unchecked")
	private class RQLLIterator implements Iterator<Item> {
		private Item[] iteratableArr = (Item[]) new Object[size];
		private int length = iteratableArr.length;

		public RQLLIterator() {
			Node startingNode = last;
			for (int i = 0; i < size; i++) {
				iteratableArr[i] = startingNode.item;
				startingNode = startingNode.previous;
			}
		}

		@Override
		public boolean hasNext() {
			return length != 0;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			while (true) {
				int r = StdRandom.uniform(0, iteratableArr.length);
				if (iteratableArr[r] == null) {
					continue;
				} else {

					Item item = iteratableArr[r];
					iteratableArr[r] = null;
					length--;
					return item;
				}
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}

	}

	private void removeNode(Node randomNodetoBeRemoved) {
		Node nextNode = randomNodetoBeRemoved.next;
		Node prevNode = randomNodetoBeRemoved.previous;
		if (prevNode != null) {
			prevNode.next = nextNode;
		}

		if (nextNode != null) {
			nextNode.previous = prevNode;
		} else {
			last = prevNode;
		}

	}

	private Node getRandomNode(Node pointer) {
		Node randomNode = last;
		int r = StdRandom.uniform(0, size);
		while (r > 0) {
			randomNode = randomNode.previous;
			r--;
		}
		return randomNode;
	}

	private void throwIfEmpty() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
	}

	private void throwIfNull(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
	}
}
