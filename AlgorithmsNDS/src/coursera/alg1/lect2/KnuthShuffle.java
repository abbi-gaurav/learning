package coursera.alg1.lect2;


import edu.princeton.cs.introcs.StdRandom;

public class KnuthShuffle<T> implements Shuffle<T> {

	@Override
	public Comparable<T>[] shuffle(Comparable<T>[] a) {
		for(int i = 0; i < a.length;i++){
			int r = StdRandom.uniform(0, i+1);
			Utils.swap(a, i, r);
		}
		return a;
	}

}
