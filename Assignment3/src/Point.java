import java.util.Comparator;

public class Point implements Comparable<Point> {
	private final int x;
	private final int y;
	public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Point that) {
		int ydiff = this.y - that.y;
		if (ydiff != 0) {
			return ydiff;
		} else {
			return this.x - that.x;
		}
	}

	public double slopeTo(Point that) {
		if (this.areEquals(that)) {
			return Double.NEGATIVE_INFINITY;
		} else if (this.y == that.y) {
			return Double.parseDouble("+0");
		} else if (this.x == that.x) {
			return Double.POSITIVE_INFINITY;
		} else {
			double ydiff = that.y - this.y;
			double xdiff = that.x - this.x;
			return ydiff / xdiff;
		}
	}

	public void draw() {
		StdDraw.point(x, y);
	}

	public void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	private boolean areEquals(Point o) {
		Point that = (Point) o;
		return this.x == that.x && this.y == that.y;
	}

	private class SlopeOrder implements Comparator<Point> {
		@Override
		public int compare(Point p1, Point p2) {
			if (p1 == null || p2 == null) {
				throw new NullPointerException();
			}
			double slopeToP1 = Point.this.slopeTo(p1);
			double slopeToP2 = Point.this.slopeTo(p2);

			if (slopeToP1 > slopeToP2)
				return +1;
			else if (slopeToP1 < slopeToP2)
				return -1;
			else
				return 0;
		}
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
