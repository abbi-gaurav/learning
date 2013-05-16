public class Subset {
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		// testDeque(args,k);
		testRandomQueue(args, k);
	}

	private static void testRandomQueue(String[] args, int k) {
		RandomizedQueue<String> rQueue = new RandomizedQueueLLBased<String>();
		while (!StdIn.isEmpty()) {
			String readString = StdIn.readString();
			rQueue.enqueue(readString);
		}
		removeAtRandom(k, rQueue);
	}

	private static void removeAtRandom(int k, RandomizedQueue<String> rQueue) {
		for (int i = 0; i < k; i++) {
			StdOut.println(rQueue.dequeue());
		}
	}

	private static void testDeque(String[] args, int k) {
		Deque<String> deque = new Deque<String>();
		while (!StdIn.isEmpty()) {
			String readString = StdIn.readString();
			deque.addFirst(readString);
		}

		removeAtRandom(k, deque);
	}

	private static void removeAtRandom(int k, Deque<String> deque) {
		for (int i = 0; i < k; i++) {
			int r = StdRandom.uniform(0, deque.size());
			if (r % 2 == 0) {
				StdOut.println(deque.removeFirst());
			} else {
				StdOut.println(deque.removeLast());
			}
		}
	}
}
