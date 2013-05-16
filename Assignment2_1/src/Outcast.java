public class Outcast {
	private final WordNet wordNet;

	public Outcast(WordNet wordnet) {
		this.wordNet = wordnet;
	}

	public String outcast(String[] nouns) {
		int maxSum = -1;
		int oc = -1;
		for (int i = 0; i < nouns.length; i++) {
			int cs = 0;

			String[] cloned = nouns.clone();
			swapWithFirst(i, cloned);
			for (int j = 1; j < cloned.length; j++) {
				cs += wordNet.distance(cloned[0], cloned[j]);
			}
			System.out.println("totalDistance to " + nouns[i] + " " + cs);

			if (cs > maxSum) {
				maxSum = cs;
				oc = i;
			}
		}

		return nouns[oc];
	}

	private void swapWithFirst(int i, Object[] nouns) {
		Object temp = nouns[0];
		nouns[0] = nouns[i];
		nouns[i] = temp;
	}

	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
		Outcast outcast = new Outcast(wordnet);
		for (int t = 2; t < args.length; t++) {
			String[] nouns = In.readStrings(args[t]);
			StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		}
	}
}
