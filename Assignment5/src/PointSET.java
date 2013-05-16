import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class PointSET {
	private final SET<Point2D> set;
	
	public PointSET(){
		this.set = new SET<Point2D>();
	}
	
	public boolean isEmpty(){
		return set.isEmpty();
	}
	
	public int size(){
		return set.size();
	}
	
	public void insert(Point2D p){
		set.add(p);
	}
	
	public boolean contains(Point2D p){
		return set.contains(p);
	}
	
	public void draw(){
		for (Point2D point : set) {
			point.draw();
		}
	}
	
	public Iterable<Point2D> range(RectHV rect){
		Set<Point2D> range = new TreeSet<Point2D>();
		for (Point2D point2d : this.set) {
			double x = point2d.x();
			double y = point2d.y();
			if((rect.xmin()<=x && x <= rect.xmax()) 
					&& (rect.ymin() <= y && y <= rect.ymax())){
				range.add(point2d);
			}
		}
		return range;
	}
	
	public Point2D nearest(Point2D p){
		if(this.set.isEmpty()){
			return null;
		}
		Iterator<Point2D> iterator = this.set.iterator();
		Point2D nearest = iterator.next();
		
		while(iterator.hasNext()){
			Point2D next = iterator.next();
			if(next.distanceTo(p) < nearest.distanceTo(p)){
				nearest = next;
			}
		}
		
		return nearest;
	}
}
