

public class KdTree {
	private static final double DOUBLE_ONE = 1.0;
	private static final double DOUBLE_ZERO = 0.0;
	private Node root;
	private int size;
	
	private static class Node{
		Point2D point;
		Node lb;
		Node rt;
		final Level level;
		public Node(Level level, Point2D point) {
			this. level = level;
			this.point = point;
		}
		
		private final int compare(Point2D that){
			return this.level.compare(this.point, that);
		}
	}
	
	public void insert(Point2D p) {
		if(contains(p)){
			return;
		}
		root = insert(root, p,Level.ODD);
		size++;
	}
	
	public void draw(){
		draw(root,createRootRect());
		StdDraw.setPenColor();
	}
	
	public Point2D nearest(Point2D query) {
		if(size == 0){
			return null;
		}
//		return nearest(root, query, root.point);
		return nearest(root, query, root.point, createRootRect());
	}
	
	public Iterable<Point2D> range(RectHV query){
		SET<Point2D> rangeVals = new SET<Point2D>();
		if(size != 0){
			range(root, query, createRootRect(),rangeVals);
		}
		return rangeVals;
	}
	
	public boolean isEmpty(){
		return root == null;
	}
	
	public int size(){
		return size;
	}
	
	public boolean contains(Point2D p){
		if(size == 0){
			return false;
		}
		return contains(root,p);
	}
	
	private boolean contains(Node node, Point2D p) {
		if(node.point.equals(p)){
			return true;
		}
		int cmp = node.compare(p);
		if(cmp > 0 &&
				node.lb != null){
			return contains(node.lb, p);
		}else if(node.rt != null){
			return contains(node.rt,p);
		}
		return false;
	}

	private RectHV createRootRect() {
		return new RectHV(DOUBLE_ZERO, DOUBLE_ZERO, root.point.x(), DOUBLE_ONE);
	}
	
	private Node insert(Node x, Point2D p, Level paretLevel) {
		if(x == null){
			return new Node(paretLevel.getChildLevel(), p);
		}
		int cmp = x.compare(p);
		if( cmp > 0 ){
			x.lb = insert(x.lb, p, x.level);
		}else {
			x.rt = insert(x.rt, p, x.level);
		}
		return x;
	}
	
	private void draw(Node p, RectHV rectHV) {
		drawSplit(p.level, rectHV);
		drawPoint(p.point);
		
		if(p.lb != null){
			draw(p.lb, getLBRectangle(p,p.lb));
		}
		
		if(p.rt != null){
			draw(p.rt, getRTRectangle(p,p.rt));
		}
	}

	private void drawPoint(Point2D point) {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		StdDraw.point(point.x(), point.y());
	}

	private void drawSplit(Level level, RectHV rectHV) {
		StdDraw.setPenRadius();
		if(level == Level.EVEN){
			StdDraw.setPenColor(StdDraw.RED);
			double xmax = rectHV.xmax();
			StdDraw.line(xmax, rectHV.ymin(), xmax, rectHV.ymax());
		}else{
			StdDraw.setPenColor(StdDraw.BLUE);
			double ymax = rectHV.ymax();
			StdDraw.line(rectHV.xmin(), ymax, rectHV.xmax(), ymax);
		}
	}

	private RectHV getRTRectangle(Node parent, Node p) {
		if(p.level == Level.EVEN){
			return new RectHV(DOUBLE_ZERO, parent.point.y(), p.point.x(), DOUBLE_ONE);
		}else{
			return new RectHV(parent.point.x(), DOUBLE_ZERO, DOUBLE_ONE, p.point.y());
		}
	}

	private RectHV getLBRectangle(Node parent, Node p) {
		if(p.level == Level.EVEN){
			return new RectHV(DOUBLE_ZERO, DOUBLE_ZERO, p.point.x(), parent.point.y());
		}else{
			return new RectHV(DOUBLE_ZERO, DOUBLE_ZERO, parent.point.x(), p.point.y());
		}
	}
	
	private Point2D nearest(Node currentNode, Point2D query, Point2D closest, RectHV nodeRect) {
		
		if(currentNode.point.distanceSquaredTo(query) < closest.distanceSquaredTo(query)){
			closest = currentNode.point;
		}
		int cmp = currentNode.compare(query);
		
		if(cmp > 0){
			if(nodeRect.distanceSquaredTo(query) < closest.distanceSquaredTo(query) && currentNode.lb != null){
				closest = nearest(currentNode.lb, query, closest, getLBRectangle(currentNode, currentNode.lb));
			}
			
			if(currentNode.rt != null){
				closest = nearest(currentNode.rt, query, closest, getRTRectangle(currentNode, currentNode.rt));
			}
		}else{
			if(currentNode.rt != null){
				closest = nearest(currentNode.rt, query, closest, getRTRectangle(currentNode, currentNode.rt));
			}
			if(nodeRect.distanceSquaredTo(query) < closest.distanceSquaredTo(query)){
				if(currentNode.lb != null){
					closest = nearest(currentNode.lb, query, closest, getLBRectangle(currentNode, currentNode.lb));
				}
			}
		}
		return closest;
	}
	
	private void range(Node node, RectHV query, RectHV currentRect, SET<Point2D> rangeVals) {
		if(query.contains(node.point)){
			rangeVals .add(node.point);
		}
		
		if(node.lb != null && currentRect.intersects(query)){
			range(node.lb, query, getLBRectangle(node, node.lb), rangeVals);
		}
		
		if(node.rt != null){
			range(node.rt, query, getRTRectangle(node, node.rt), rangeVals);
		}
	}

	private static enum Level {
		EVEN {
			@Override
			Level getChildLevel() {
				return ODD;
			}

			@Override
			int compare(Point2D p1, Point2D p2) {
				double p1x = p1.x();
				double p2x = p2.x();
				if(p1x > p2x) return +1;
				if(p1x < p2x) return -1;
				return 0;
			}

			@Override
			double getSquaredDistanceFromSplitLine(Point2D p1, Point2D p2) {
				double dx = p1.x() - p2.x();
				return dx*dx;
			}
		},
		ODD {
			@Override
			Level getChildLevel() {
				return EVEN;
			}

			@Override
			int compare(Point2D p1, Point2D p2) {
				double p1y = p1.y();
				double p2y = p2.y();
				if(p1y > p2y) return +1;
				if(p1y < p2y) return -1;
				return 0;
			}

			@Override
			double getSquaredDistanceFromSplitLine(Point2D p1, Point2D p2) {
				double dy = p1.y() - p2.y();
				return dy*dy;
			}

		};

		abstract Level getChildLevel();
		abstract int compare (Point2D p1, Point2D p2);
		abstract double getSquaredDistanceFromSplitLine(Point2D p1, Point2D p2);
	}
}
