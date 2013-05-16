import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class KdTreeTest2 {
	private static final String DIR = "/home/gaurav_abbi/learning/coursera/data/kdtree/";
	KdTree kdTree;
	Point2D p1 = null;
	Point2D p2 = null;
	Point2D p3 = null;
	Point2D p4 = null;
	Point2D p5 = null;

	@Before
	public void setup() {
		kdTree = new KdTree();
		p1 = new Point2D(0.7, 0.2);
		p2 = new Point2D(0.5, 0.4);
		p3 = new Point2D(0.2, 0.3);
		p4 = new Point2D(0.4, 0.7);
		p5 = new Point2D(0.9, 0.6);

		kdTree.insert(p1);
		kdTree.insert(p2);
		kdTree.insert(p3);
		kdTree.insert(p4);
		kdTree.insert(p5);

		assertFalse(kdTree.isEmpty());
		assertEquals(5, kdTree.size());

	}

	@Test
	public void testCircle() {
		String filename = DIR+"circle10.txt";
		In in = new In(filename);

		KdTree kdtree = new KdTree();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			kdtree.insert(p);
		}
		assertEquals(new Point2D(0.975528, 0.345492),
				kdtree.nearest(new Point2D(0.81, 0.3)));
	}

	@Test
	public void testCircle2() {
		String filename = DIR+"circle10.txt";
		In in = new In(filename);

		KdTree kdtree = new KdTree();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			kdtree.insert(p);
		}
		assertEquals(new Point2D(0.024472, 0.654508),
				kdtree.nearest(new Point2D(0.09387192953085366,
						0.7347367703418718)));
	}

	@Test
	public void testRandom() {
		String[] filenames = new String[] { DIR+"circle10.txt",
				DIR+"vertical7.txt", DIR+"horizontal8.txt",
				DIR+"input10K.txt" };

		for (String filename : filenames) {
			In in = new In(filename);

			KdTree kdtree = new KdTree();
			PointSET pointSet = new PointSET();

			while (!in.isEmpty()) {
				double x = in.readDouble();
				double y = in.readDouble();
				Point2D p = new Point2D(x, y);
				kdtree.insert(p);
				pointSet.insert(p);
			}

			for (int i = 0; i < 1000; i++) {
				double x = StdRandom.uniform();
				double y = StdRandom.uniform();
				assertEquals("broke on " + filename + " seed [" + i + "] " + x
						+ "," + y, pointSet.nearest(new Point2D(x, y)),
						kdtree.nearest(new Point2D(x, y)));
			}
		}
	}

	@Test
	public void testRandomTiming() {
		String filename = DIR+"input10K.txt";
		In in = new In(filename);
		KdTree kdtree = new KdTree();

		long totalKdTreeTime = 0;
		long start = 0;
		int numberOfOps = 10000;

		start = System.currentTimeMillis();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			kdtree.insert(p);
		}
		totalKdTreeTime = System.currentTimeMillis() - start;

		long opsPerSec = numberOfOps / (totalKdTreeTime) * 1000;

		System.out.println("Total KdTree Time for " + numberOfOps
				+ " insert ops: " + totalKdTreeTime + " ms. Ops/sec: "
				+ opsPerSec);

		totalKdTreeTime = 0;
		start = System.currentTimeMillis();
		for (int i = 0; i < numberOfOps; i++) {
			double x = StdRandom.uniform();
			double y = StdRandom.uniform();
			Point2D nearestUsingKdTree = kdtree.nearest(new Point2D(x, y));
		}
		totalKdTreeTime = System.currentTimeMillis() - start;
		opsPerSec = numberOfOps / (totalKdTreeTime) * 1000;
		System.out.println("Total KdTree Time for " + numberOfOps
				+ " nearest ops: " + totalKdTreeTime + " ms. Ops/sec: "
				+ opsPerSec);
		assertTrue(opsPerSec>500000);
	}

	@Test
	public void testConstructorInitializesEmptySet() {
		KdTree kdTree = new KdTree();
		assertTrue(kdTree.isEmpty());
		assertEquals(0, kdTree.size());
	}

	@Test
	public void testInsertPoint() {
		assertTrue(kdTree.contains(p1));
		assertTrue(kdTree.contains(p2));
		assertTrue(kdTree.contains(p3));
		assertTrue(kdTree.contains(p4));
		assertTrue(kdTree.contains(p5));
		assertFalse(kdTree.contains(new Point2D(1, 0)));
	}

	@Test
	public void testInsertSameTwice() {
		KdTree kdTree = new KdTree();
		kdTree.insert(new Point2D(0, 0));
		kdTree.insert(new Point2D(0, 0));
		assertEquals(1, kdTree.size());
	}

	@Test
	public void testNearestOnEmpty() {
		KdTree kdTree = new KdTree();
		assertEquals(null, kdTree.nearest(new Point2D(0.2, 0.2)));
	}

	@Test
	public void testRangeOnEmpty() {
		KdTree kdTree = new KdTree();
		RectHV rect = new RectHV(0, 0, 0.5, 0.5);
		Set<Point2D> pointSetInsideRectangle = new TreeSet<Point2D>();
		assertEquals(pointSetInsideRectangle.iterator().hasNext(), kdTree.range(rect).iterator().hasNext());

		assertEquals(null, kdTree.nearest(new Point2D(0.2, 0.2)));
	}

	@Test
	public void nearest() {
		assertEquals(new Point2D(0.2, 0.3),
				kdTree.nearest(new Point2D(0.2, 0.2)));
	}

	@Test
	public void nearest2() {
		assertEquals(new Point2D(0.9, 0.6),
				kdTree.nearest(new Point2D(0.91, 0.6)));
	}

	@Test
	public void nearest3() {
		assertEquals(new Point2D(0.5, 0.4),
				kdTree.nearest(new Point2D(0.51, 0.4)));
	}

	@Test
	public void nearestIsRoot() {
		assertEquals(new Point2D(0.7, 0.2),
				kdTree.nearest(new Point2D(0.7, 0.21)));
	}

	@Test
	public void insideRectangle() {
		RectHV rect = new RectHV(0, 0, 0.5, 0.5);
		SET<Point2D> pointSetInsideRectangle = new SET<Point2D>();
		pointSetInsideRectangle.add(new Point2D(0.5, 0.4));
		pointSetInsideRectangle.add(new Point2D(0.2, 0.3));
		assertEquals(pointSetInsideRectangle, kdTree.range(rect));
	}

	@Test
	public void insideRectangleMatchedNone() {
		RectHV rect = new RectHV(0, 0, 0.1, 0.1);
		SET<Point2D> pointSetInsideRectangle = new SET<Point2D>();
		assertEquals(pointSetInsideRectangle, kdTree.range(rect));
	}
}