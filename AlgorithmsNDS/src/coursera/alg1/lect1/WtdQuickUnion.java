package coursera.alg1.lect1;

import java.util.Arrays;

public class WtdQuickUnion implements Unionable {

	private final int[] id;
	private final int[] sz;

	public WtdQuickUnion(int n) {
		id = Utils.initArray(n);
		sz = new int[n];
		Arrays.fill(sz, 1);
	}

	private int root(int i) {
		while (i != id[i]) {
			id[i] = id[id[i]];
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
		if (sz[i] < sz[j]) {
			id[i] = j;
			sz[j] += sz[i];
		} else {
			id[j] = i;
			sz[i] += sz[j];
		}
	}

	@Override
	public String toString() {
		String header = Arrays.toString(Utils.initArray(id.length));
		String array = Arrays.toString(id);
		return header + "\n" + array;
	}
}
