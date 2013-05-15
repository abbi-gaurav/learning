package coursera.alg1.lect2;

import java.util.Arrays;


public class GrahmScan { 
	
	public Stack<Point2D> findConvexHull(Point2D[] a){
		Arrays.sort(a, Point2D.Y_ORDER);  //sort by y value
		
		Arrays.sort(a, 1, a.length, a[0].POLAR_ORDER); //sort by polar angle w.r.t to a[0] i.e. smallest y value
		
		Stack<Point2D> convexHull = new ResizingArrayBasedStack<>();
		convexHull.push(a[0]);
		convexHull.push(a[1]);
		
		for (int i = 2; i < a.length; i++) {
			Point2D top = convexHull.pop();
			while (Point2D.ccw(convexHull.peek(), top, a[i]) <= 0) {
				top = convexHull.pop();
			}
			convexHull.push(top);
			convexHull.push(a[i]);
		}
		return convexHull;
	}
}
