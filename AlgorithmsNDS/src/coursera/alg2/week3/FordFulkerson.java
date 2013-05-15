package coursera.alg2.week3;

import coursera.alg1.lect2.LinkedQueue;
import coursera.alg1.lect2.Queue;

public class FordFulkerson {
	private boolean[] marked;
	private FlowEdge[] edgeTo;
	private double value;
	
	public FordFulkerson(FlowNetwork graph, int s, int t){
		value = 0.0;
		
		while(hasAugmentingPath(graph, s, t)){
			double bottle = Double.POSITIVE_INFINITY;
			for(int x = t; x != s; x = edgeTo[x].other(x)){
				bottle = Math.min(bottle, edgeTo[x].residualCapacityTo(x));
			}
			
			for(int x = t; x != s; x = edgeTo[x].other(x)){
				edgeTo[x].addResidualFlowTo(x, bottle);
			}
			
			value += bottle;
		}
	}
	
	public double value(){
		return value;
	}
	
	public boolean inCut(int v){
		return marked[v];
	}
	
	private boolean hasAugmentingPath(FlowNetwork graph, int s, int t){
		marked = new boolean[graph.getV()];
		edgeTo = new FlowEdge[graph.getV()];
		Queue<Integer> queue = new LinkedQueue<>();
		queue.enqueue(s);
		marked[s] = true;
		
		while(!queue.isEmpty()){
			int v = queue.dequeue();
			for(FlowEdge e:graph.adj(v)){
				int w = e.other(v);
				if(e.residualCapacityTo(w) > 0 && !marked[w]){
					marked[w] = true;
					edgeTo[w] = e;
					queue.enqueue(w);
				}
			}
		}
		return marked[t];
	}
	
	public static void main(String[] args) {
		
	}
}
