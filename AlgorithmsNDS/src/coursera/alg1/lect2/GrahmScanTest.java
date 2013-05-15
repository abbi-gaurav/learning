package coursera.alg1.lect2;

import org.junit.Test;

public class GrahmScanTest {
	
	@Test
	public void testExercise(){
		Point2D[] points = getInput();
		System.out.println(points);
		Stack<Point2D> hull = new GrahmScan().findConvexHull(points);
		printHull(hull);
	}

	private void printHull(Stack<Point2D> hull) {
		for (Point2D point : hull) {
			System.out.println(point);
		}
	}

	/**
	 * @return
	 */
	public Point2D[] getInput() {
		Point2D[] points = new Point2D[10];
		points[0]= new Point2D(2.0, 9.0);
		points[1]= new Point2D(3.0, 6.0);
		points[2]= new Point2D(7.0, 5.0);
		points[3]= new Point2D(8.0, 8.0);
		points[4]= new Point2D(4.0, 2.0);
		points[5]= new Point2D(0.0, 0.0);
		points[6]= new Point2D(9.0, 4.0);
		points[7]= new Point2D(1.0, 3.0);
		points[8]= new Point2D(6.0, 1.0);
		points[9]= new Point2D(5.0, 7.0);
		return points;
	}
}
