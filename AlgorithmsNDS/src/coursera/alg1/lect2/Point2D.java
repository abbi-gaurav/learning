package coursera.alg1.lect2;

import java.util.Comparator;

public class Point2D {
	public static final Comparator<Point2D> Y_ORDER = new YOrder();
	public final Comparator<Point2D> POLAR_ORDER = new PolarOrder();
	
	private final double x;
	private final double y;

	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public static int ccw(Point2D a, Point2D b, Point2D c){
		double area = (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
		if		(area < 0)	return	-1; 	//cw
		else if	(area > 0) 	return	+1;		//ccw
		else				return	0; 		//collinear
	}
	
	private class PolarOrder implements Comparator<Point2D>{
		@Override
		public int compare(Point2D q1, Point2D q2) {
			double dy1 = q1.y - y;
			double dy2 = q2.y - y;
			if		(dy1 == 0 && dy2 == 0)	return 0;
			else if	(dy1 >= 0 && dy2 < 0)	return -1;
			else if	(dy2 >= 0 && dy1 < 0)	return +1;
			else return -ccw(Point2D.this, q1, q2);
		}
	}
	
	private static final class YOrder implements Comparator<Point2D>{
		@Override
		public int compare(Point2D p1, Point2D p2) {
			return Double.compare(p1.y, p2.y);
		}
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
