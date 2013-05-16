import org.junit.Assert;
import org.junit.Test;

public class PointTest {
	@Test
	public void testSlopeEquality() {
		Point p = new Point(10000, 0);
		Point q = new Point(7000, 3000);
		Point r = new Point(3000, 7000);
		Point s = new Point(0, 10000);

		Assert.assertEquals(p.slopeTo(q), p.slopeTo(r), 0.0);
		Assert.assertEquals(p.slopeTo(r), p.slopeTo(s), 0.0);
	}

	@Test
	public void testGraderSlopeTo() {
		Point p = new Point(434, 155);
		Point q = new Point(424, 241);
		Point r = new Point(434, 155);
		Assert.assertEquals(+1, p.SLOPE_ORDER.compare(q, r));
		Assert.assertEquals(Double.NEGATIVE_INFINITY, p.slopeTo(r), 0.0);

		p = new Point(4, 0);
		q = new Point(5, 0);
		r = new Point(4, 0);
		Assert.assertEquals(1, p.SLOPE_ORDER.compare(q, r));
	}
}
