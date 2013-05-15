package coursera.alg1.lect1;

import java.util.Arrays;

public class QuickUnion implements Unionable {

	private final int[] id;

	public QuickUnion(int n) {
		id = Utils.initArray(n);
	}

	private int root(int i) {
		while (i != id[i]) {
			i = id[i];
		}
		return i;
	}

	@Override
	public boolean isConnected(int p, int q) {
		return root(p) == root(q);
	}

	@Override
	public void union(int p, int q) {
		int i = root(p);
		int j = root(q);
		id[i] = j;
	}

	@Override
	public String toString() {
		return Arrays.toString(id);
	}

}
