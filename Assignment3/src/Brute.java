import java.util.Arrays;

public class Brute {

	public static void main(String[] args) {
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);

		Point[] points = readInput(args[0]);
		findCollinears(points);
		StdDraw.show(0);
	}

	private static void findCollinears(Point[] points) {
		StdDraw.setPenRadius(0.002);
		for (int p = 0; p < points.length - 3; p++) {
			for (int q = p + 1; q < points.length - 2; q++) {
				double slopePToQ = points[p].slopeTo(points[q]);
				for (int r = q + 1; r < points.length - 1; r++) {
					double slopePToR = points[p].slopeTo(points[r]);
					if (slopePToQ == slopePToR) {
						for (int s = r + 1; s < points.length; s++) {
							double slopePToS = points[p].slopeTo(points[s]);
							if (slopePToQ == slopePToS) {
								System.out.println(points[p] + " -> "
										+ points[q] + " -> " + points[r]
										+ " -> " + points[s]);
								points[s].drawTo(points[p]);
							}
						}
					}
				}
			}
		}
	}

	private static Point[] readInput(String fileName) {
		In in = new In(fileName);
		Point[] points = new Point[in.readInt()];
		for (int i = 0; i < points.length; i++) {
			points[i] = new Point(in.readInt(), in.readInt());
			points[i].draw();
		}
		Arrays.sort(points);
		return points;
	}
}
