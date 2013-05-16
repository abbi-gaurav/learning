public class PercolationStats {

	private final double[] thresholds;
	private final int n;
	private boolean done = false;

	public PercolationStats(int n, int t) {
		if (n <= 0 || t <= 0) {
			throw new IllegalArgumentException(
					"size and no of runs should be positive");
		}
		this.n = n;
		this.thresholds = new double[t];
	}

	public double mean() {
		initMC();
		return StdStats.mean(thresholds);
	}

	private void initMC() {
		if (!done) {
			monteCarlo();
		}
	}

	public double stddev() {
		initMC();
		return StdStats.stddev(thresholds);
	}

	public double confidenceLo() {
		return mean() - confidenceVariation();
	}

	public double confidenceHi() {
		return mean() + confidenceVariation();
	}

	public static void main(String[] args) {
		PercolationStats percolationStats = new PercolationStats(
				Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		percolationStats.monteCarlo();
		percolationStats.printStats();
	}

	private void monteCarlo() {
		for (int i = 0; i < thresholds.length; i++) {
			doSingle(i);
		}
		done = true;
	}

	private void doSingle(int index) {
		Percolation tester = new Percolation(n);
		int openedSites = 0;
		while (true) {
			int row = generateVal();
			int column = generateVal();
			if (!tester.isOpen(row, column)) {
				tester.open(row, column);
				openedSites++;
				if (tester.percolates()) {
					thresholds[index] = ((double) openedSites) / (n * n);
					return;
				}
			}
		}
	}

	private int generateVal() {
		return StdRandom.uniform(1, n + 1);
	}

	private void printStats() {
		System.out.println("mean[" + mean() + "]");
		System.out.println("stddev[" + mean() + "]");
		System.out.println("95% confidence interval[" + confidenceLo() + ","
				+ confidenceHi() + "]");
	}

	private double confidenceVariation() {
		return (1.96 * stddev()) / (Math.sqrt(thresholds.length));
	}

}
