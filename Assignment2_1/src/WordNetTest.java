public class WordNetTest {
	private static final String DIR = "/home/gaurav_abbi/learning/coursera/data/wordnet/";

	public static WordNet testConstruction(String synsets, String hypernyms) {
		Stopwatch stopwatch = new Stopwatch();
		StdOut.println("Testing WordNet construction time...");
		WordNet wordnet = new WordNet(synsets, hypernyms);
		StdOut.printf("Construction took: %f milliseconds\n",
				stopwatch.elapsedTime());
		return wordnet;
	}

	public static void testStandardChecklist(WordNet wordnet) {
		StdOut.println("Testing checklist tests...");
		Stopwatch stopwatch = new Stopwatch();
		if (wordnet.distance("municipality", "region") != 3) {
			StdOut.println("    Test 1 failed.");
		}
		if (wordnet.distance("individual", "physical_entity") != 2) {
			StdOut.println("    Test 2 failed.");
		}
		if (wordnet.distance("edible_fruit", "physical_entity") != 5) {
			StdOut.println("    Test 3 failed.");
		}
		if (!wordnet.sap("individual", "edible_fruit")
				.equals("physical_entity")) {
			StdOut.println("    Test 4 failed.");
		}
		if (wordnet.distance("Black_Plague", "black_marlin") != 33) {
			StdOut.println("    Test 5 failed.");
		}
		if (wordnet.distance("American_water_spaniel", "histology") != 27) {
			StdOut.println("    Test 6 failed.");
		}
		if (wordnet.distance("Brown_Swiss", "barrel_roll") != 29) {
			StdOut.println("    Test 7 failed.");
		}
		StdOut.printf("Checklist tests took: %f milliseconds\n",
				stopwatch.elapsedTime());
	}

	public static void testExtraTests(WordNet wordnet) {
		StdOut.println("Testing extra tests...");
		In nouns1 = new In(DIR + "nouns1.txt");
		Queue<String> nounQueue1 = new Queue<String>();
		Queue<String> nounQueue2 = new Queue<String>();
		Queue<Integer> distanceQueue = new Queue<Integer>();
		Queue<String> ancestorQueue = new Queue<String>();
		while (nouns1.hasNextLine()) {
			nounQueue1.enqueue(nouns1.readLine());
		}
		In nouns2 = new In(DIR + "nouns2.txt");
		while (nouns2.hasNextLine()) {
			nounQueue2.enqueue(nouns2.readLine());
		}
		In distances = new In(DIR + "distances.txt");
		while (distances.hasNextLine()) {
			distanceQueue.enqueue(Integer.parseInt(distances.readLine()));
		}
		In ancestors = new In(DIR + "ancestors.txt");
		while (ancestors.hasNextLine()) {
			ancestorQueue.enqueue(ancestors.readLine());
		}
		int length_errors = 0;
		int ancestor_errors = 0;
		Stopwatch stopwatch = new Stopwatch();
		while (!nounQueue1.isEmpty()) {
			String noun1 = nounQueue1.dequeue();
			String noun2 = nounQueue2.dequeue();
			int distance = distanceQueue.dequeue();
			String ancestor = ancestorQueue.dequeue();
			if (wordnet.distance(noun1, noun2) != distance) {
				length_errors++;
			}
			if (!wordnet.sap(noun1, noun2).equals(ancestor)) {
				ancestor_errors++;
			}
		}
		StdOut.printf("Extra tests took: %f milliseconds\n",
				stopwatch.elapsedTime());
		StdOut.printf("Length errors: %d SAP errors: %d\n", length_errors,
				ancestor_errors);

	}

	public static void main(String[] args) {
		WordNet wordnet = testConstruction(args[0], args[1]);
		testStandardChecklist(wordnet);
		testExtraTests(wordnet);
	}
}