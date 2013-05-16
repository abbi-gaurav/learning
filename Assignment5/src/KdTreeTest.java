import junit.framework.Assert;

import org.junit.Test;


public class KdTreeTest {
	
	private final KdTree kdTree;

	public KdTreeTest(String fileName) {
		this.kdTree = KdTreeTest.add(fileName);
	}
	@Test
	public void testAPIs(){
		Assert.assertTrue("should not be empty", !kdTree.isEmpty());
		
	}
	public static void main(String[] args) {
		StdDraw.setScale(0.0, 1.0);
		KdTree tree = KdTreeTest.add(args[0]);
		tree.draw();
		
		RectHV query = new RectHV(0.8, 0.4, 1.0, 0.7);
		query.draw();
		
		Iterable<Point2D> range = tree.range(query);
		printRange(range);
		
		Point2D point = new Point2D(0.81,0.30);
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.point(point.x(), point.y());
		
		Point2D np = tree.nearest(point);
		StdDraw.setPenColor();
		StdDraw.setPenRadius();
		StdDraw.line(np.x(), np.y(), point.x(), point.y());
	}

	private static void printRange(Iterable<Point2D> range) {
		for (Point2D point2d : range) {
			System.err.print(point2d+"|");
		}
		System.out.println();
	}

	private static KdTree add(String fileName) {
		KdTree tree = new KdTree();
		In in = new In(fileName);
		while(!in.isEmpty()){
			tree.insert(new Point2D(in.readDouble(), in.readDouble()));
		}
		return tree;
	}
}
