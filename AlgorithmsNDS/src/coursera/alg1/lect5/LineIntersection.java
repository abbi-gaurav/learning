package coursera.alg1.lect5;

import java.util.Iterator;

import coursera.alg1.lect4.BHBasedMinPrioritQ;
import coursera.alg1.lect4.BST;
import coursera.alg1.lect4.MinPriorityQueue;
import coursera.alg1.lect5.Line.Point;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdRandom;

public class LineIntersection{
	
	public static void main(String[] args) {
		Line[] lines = readData(args[0]);
//		drawLines(lines);
		MinPriorityQueue<Point> xBasedPQ = getPriorityQueue(lines);
		sweepLineAlgo(xBasedPQ);
	}

	private static void drawLines(Line[] lines) {
		StdDraw.clear();
		for(Line line:lines){
			Point p1 = line.getP1();
			Point p2 = line.getP2();
			StdDraw.point(p1.getX(), p1.getY());
			StdDraw.point(p2.getX(), p2.getY());
			StdDraw.line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
			StdDraw.show(50);
		}
	}

	private static void sweepLineAlgo(MinPriorityQueue<Point> xBasedPQ) {
		
		BST<Integer, Point> yBst = new BST<>();
		
		while(!xBasedPQ.isEmpty()){
			Point point = xBasedPQ.delMin();
			int y = point.getY();
			
			//if true, we have hit end of a line, remove it
			if(yBst.contains(y)){
				yBst.delete(y);
				continue;
			}
			
			LineType pointType = point.getType();
			if(pointType == LineType.HORIZONTAL){
				yBst.put(y, point);
			}else if(pointType == LineType.VERTICAL){
				if(yBst.size()  > 0){
					Line line = point.getLine();
					System.out.println("Lines interesction for vertical line:"+line);
					Iterator<Integer> itr = yBst.range(line.getP1().getY(), line.getP2().getY()).iterator();
					print(itr,yBst);
				}
			}
		}
	}

	private static void print(Iterator<Integer> itr, BST<Integer, Point> bst) {
		if(!itr.hasNext()){
			return;
		}
		
		while(itr.hasNext()){
			System.out.println(bst.get(itr.next()).getLine());
		}
	}

	private static MinPriorityQueue<Point> getPriorityQueue(Line[] lines) {
		MinPriorityQueue<Line.Point> queue = new BHBasedMinPrioritQ<>(
				2 * lines.length);
		
		for (Line line : lines) {
			queue.insert(line.getP1());
			if(line.getType() == LineType.HORIZONTAL){
				queue.insert(line.getP2());
			}
		}
		
		return queue;
	}

	private static Line[] readData(String fileName) {
		In in = new In(fileName);
		Line[] lines = new Line[in.readInt()];
		
		for(int i=0;!in.isEmpty();i++){
			lines[i] = new Line(in.readInt(), in.readInt(), in.readInt(), in.readInt());
		}
		return lines;
	}
	
}