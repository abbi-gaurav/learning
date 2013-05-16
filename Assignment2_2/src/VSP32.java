import java.util.Arrays;

public class VSP32 {
	private int[] path;
	private double[][] ea;
	private final double[] distTos;
	private final int[] edgeTos;

	public VSP32(double[][] ea) {
		int h = ea.length;
		int w = ea[0].length;
		this.ea = ea;
		
		TopologicalSort2 order = new TopologicalSort2(w, h);
		
		distTos = new double[h*w+2];
		Arrays.fill(distTos, Double.POSITIVE_INFINITY);
		distTos[h*w] = 0.0;
		
		edgeTos = new int[h*w+2];
		
		for(int p : order.order()){
			for(int adj:Utils.getadj2(w, h, p)){
				relax(p,adj,h,w);
			}
		}
		
		path = path(h*w+1,h,w);
	}

	private void relax(int p, int adj, int h, int w) {
		if(distTos[adj] > distTos[p]+getEnery(adj, w, h)){
			distTos[adj] = distTos[p]+getEnery(adj, w, h);
			edgeTos[adj] = p;
		}
	}
	
	private double getEnery(int p, int w,int h){
		if(p == h*w){
			return 0.0;
		}else if (p == h*w+1){
			return 7.0;
		}
		int ip = p/w;
		int jp = p%w;
		
		return ea[ip][jp];
	}
	
	public int[] seam(){
		return path;
	}
	
	private int[] path(int v, int h, int w){
		int[] path = new int[h];
		int count = path.length;
		for(int e = edgeTos[v]; e != h*w; e = edgeTos[e]){
			path[--count] = e%w;
		}
		return path;
	}
}
