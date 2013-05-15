package coursera.alg1.lect1;

import java.util.Arrays;

public class QuickFind implements Unionable {
	private final int[] id;

	public QuickFind(int n) {
		id = Utils.initArray(n);
	}

	@Override
	public boolean isConnected(int p, int q) {
		return id[p] == id[q];
	}

	@Override
	public void union(int p, int q) {
		int pId = id[p];
		int qId = id[q];

		for (int i = 0; i < id.length; i++) {
			if (id[i] == pId) {
				id[i] = qId;
			}
		}
	}

	@Override
	public String toString() {
		return Arrays.toString(id);
	}

}
