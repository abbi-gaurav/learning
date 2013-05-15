package coursera.alg2.week3.test;

import coursera.alg2.week1.test.GraphTestUtils;
import coursera.alg2.week3.FlowEdge;
import coursera.alg2.week3.FlowNetwork;
import edu.princeton.cs.introcs.In;

public class Utils {
	public static FlowNetwork create(String fileName){
		In in = new In(GraphTestUtils.DIR+fileName);
		FlowNetwork fn = new FlowNetwork(in.readInt());
		int e = in.readInt();
		while(!in.isEmpty()){
			fn.addEdge(new FlowEdge(in.readInt(), in.readInt(), in.readDouble()));
		}
		
		return fn;
	}
}
