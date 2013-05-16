import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class RandomizedQueueArrayBase<Item> implements RandomizedQueue<Item> {
	private Item[] store;
	private int last;
	private int size;

	public RandomizedQueueArrayBase() {
		this.store = (Item[]) new Object[1];
		this.last = 0;
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void enqueue(Item item) {
		throwIfNull(item);
		if (last == store.length) {
			resize((size * 2) + 1);
		}
		this.store[last++] = item;
		size++;
	}

	private void throwIfNull(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
	}

	private void resize(int size) {
		Item[] newStore = (Item[]) new Object[size];
		int j = 0;
		for (int i = 0; i < store.length; i++) {
			if (store[i] != null) {
				newStore[j++] = store[i];
			}
		}
		this.store = newStore;
		this.last = j;
	}

	@Override
	public Item dequeue() {
		throwIfEmpty();

		if (size == store.length / 4) {
			resize(store.length / 2);
		}

		SampleInfo pickRandom = pickRandom(store);
		Item sample = pickRandom.item;

		size--;
		store[pickRandom.index] = null;
		return sample;
	}

	@Override
	public Item sample() {
		throwIfEmpty();
		return pickRandom(store).item;
	}

	private SampleInfo pickRandom(Item[] array) {
		while (true) {
			int index = StdRandom.uniform(0, array.length);
			Item item = array[index];
			if (item == null) {
				continue;
			}
			return new SampleInfo(index, item);
		}
	}

	private void throwIfEmpty() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
	}

	private class SampleInfo {
		final int index;
		final Item item;

		public SampleInfo(int index, Item item) {
			this.index = index;
			this.item = item;
		}
	}

	private class RQIterator implements Iterator<Item> {
		private Item[] iteratorItems = (Item[]) new Object[size];
		private int itemsLeft = iteratorItems.length;

		public RQIterator() {
			for (int i = 0, j = 0; i < store.length; i++) {
				if (store[i] != null) {
					iteratorItems[j++] = store[i];
				}
			}
		}

		@Override
		public boolean hasNext() {
			return itemsLeft != 0;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			SampleInfo pickRandom = pickRandom(iteratorItems);
			Item item = pickRandom.item;

			itemsLeft--;
			iteratorItems[pickRandom.index] = null;

			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Iterator<Item> iterator() {
		return new RQIterator();
	}

}
