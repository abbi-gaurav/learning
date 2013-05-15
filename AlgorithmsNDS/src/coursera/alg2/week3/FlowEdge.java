package coursera.alg2.week3;

public class FlowEdge {
	private final double capacity;
	private final int w;
	private final int v;
	private int flow;
	
	public FlowEdge(int from, int to, double capacity) {
		this.v = from;
		this.w = to;
		this.capacity = capacity;
	}
	
	public int from(){
		return v;
	}
	
	public int to(){
		return w;
	}
	
	public double capcity(){
		return capacity;
	}
	
	public double flow(){
		return flow;
	}
	
	public int other(int x){
		if(x == this.v){
			return w;
		}
		
		if(x == this.w){
			return this.v;
		}
		
		throw new IllegalArgumentException();
	}
	
	/**
	 * residual capacity toward x
	 * @param x
	 * @return
	 */
	public double residualCapacityTo(int x){
		if(x == this.w){
			return capacity-flow;
		}
		
		if(x == this.v){
			return flow;
		}
		
		throw new IllegalArgumentException();
	}
	/**
	 * add resudual flow toward x 
	 * @param x
	 * @param delta
	 */
	public void addResidualFlowTo(int x, double delta){
		if(x == v){
			flow -= delta;
		}else if(x == w){
			flow += delta;
		}else{
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public String toString() {
		return v+"--"+flow+"/"+capacity+"-->"+w;
	}
}
