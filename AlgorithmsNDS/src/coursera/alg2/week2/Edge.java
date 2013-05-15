package coursera.alg2.week2;

public class Edge implements Comparable<Edge>{
	private final int w;
	private final int v;
	private final double weight;
	
	public Edge(int v, int w, double weight){
		this.v = v;;
		this.w = w;
		this.weight = weight;
	}
	
	@Override
	public int compareTo(Edge that) {
		if(this.weight < that.weight)		return -1;
		if(this.weight > that.weight)		return +1;
		else								return 0;
	}

	public double getWeight() {
		return weight;
	}
	
	public int either(){
		return v;
	}
	
	public int other(int x){
		return x == v? w:v;
	}
	
	public String toString(){ 
		return String.format("%d-%d %.2f", v, w, weight);
	}


}
