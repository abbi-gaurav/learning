package coursera.alg1.lect5;

public class Line {
	
	private final Point p1;
	private final Point p2;
	
	public Line(int x1, int y1, int x2, int y2) {
		this.p1 = new Point(x1, y1);
		this.p2 = new Point(x2, y2);
	}
	
	public LineType getType(){
		if(p1.getX() == p2.getX()){
			return LineType.VERTICAL;
		}else if(p1.getY() == p2.getY()){
			return LineType.HORIZONTAL;
		}
		return LineType.INCLINED;
	}
	
	@Override
	public String toString() {
		return "["+p1.toString()+","+p2.toString()+"]";
	}
	
	public Point getP1() {
		return p1;
	}
	
	public Point getP2() {
		return p2;
	}
	
	public final class Point implements Comparable<Point>{
		private final int x;
		private final int y;
		
		private Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		@Override
		public int compareTo(Point that) {
			return Integer.compare(this.x, that.x);
		}
		
		@Override
		public String toString() {
			return "("+x+","+y+")";
		}
		
		public LineType getType(){
			return Line.this.getType();
		}
		
		public Line getLine(){
			return Line.this;
		}
	}
}
