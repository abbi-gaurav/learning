import java.util.Arrays;
import java.util.Stack;

public class Fast {
	public static void main(String[] args) {
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);

		Point[] points = readInput(args[0]);
		findCollinear(points);
	}

	private static void findCollinear(Point[] copy) {
		if (copy.length < 4)
			return;

		for (int p = 0; p < copy.length; p++) {
			Point[] points = new Point[copy.length];
			System.arraycopy(copy, 0, points, 0, copy.length);

			Arrays.sort(points, copy[p].SLOPE_ORDER);

			Point origin = points[0];
			Stack<Point> stack = new Stack<Point>();

			int qRef = 1;
			stack.push(origin);
			stack.push(points[qRef]);

			while (qRef < points.length - 1) {
				if (origin.slopeTo(stack.peek()) == origin
						.slopeTo(points[++qRef])) {
					stack.push(points[qRef]);
				} else {
					// if (stack.size() >= 4
					// && !(isDrawnAlready(origin,
					// origin.slopeTo(stack.peek()), drawnLines))) {
					if (stack.size() >= 4
							&& !isAlreadyDrawn(copy, p, origin, stack)) {
						drawLine(stack);
					}
					stack.clear();
					stack.push(origin);
					stack.push(points[qRef]);
				}
			}
			if (stack.size() >= 4 && !isAlreadyDrawn(copy, p, origin, stack)) {
				drawLine(stack);
			}
		}
	}

	private static boolean isAlreadyDrawn(Point[] copy, int p, Point origin,
			Stack<Point> stack) {
		if (p < 1) {
			return false;
		}

		Point[] collinears = stack.toArray(new Point[0]);
		Arrays.sort(collinears);
		int compareToPrev = copy[p - 1].compareTo(collinears[0]);
		if (compareToPrev >= 0) {
			return true;
		}

		return false;
	}

	private static void drawLine(Stack<Point> stack) {
		// print the line
		Point[] collinears = stack.toArray(new Point[stack.size()]);
		Arrays.sort(collinears);
		collinears[0].drawTo(collinears[collinears.length - 1]);
		printCollinear(collinears);
	}

	private static void printCollinear(Point[] collinears) {
		System.out.print(collinears[0]);
		for (int i = 1; i < collinears.length; i++) {
			System.out.print(" -> ");
			System.out.print(collinears[i]);
		}
		System.out.println();
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
