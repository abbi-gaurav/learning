import java.util.Iterator;


public class VSP3 {
	private int[] path;

	@SuppressWarnings("unchecked")
	public VSP3(double[][] ea) {
		int w = ea[0].length;
		int h = ea.length;
		
		Stack<Integer>[] paths = new Stack[w];
		for(int i =0; i<paths.length;i++){
			paths[i] = new Stack<Integer>();
			paths[i].push(i);
		}
		
		double[] distTo = new double[w];
		for(int j = 0; j<w;j++){
			distTo[j] = ea[0][j];
		}
		
		for(int i = 0; i< h-1;i++){
			for(int j = 0; j<w;j++){
				double nextDistTo = Double.POSITIVE_INFINITY;
				
				int p = paths[j].peek();
				int [] adjs = getadj(w, i, p);
				
				for(int adj:adjs){
					nextDistTo = relax(adj,i,distTo[j],paths[j],ea,nextDistTo);
				}
				distTo[j] = nextDistTo;
			}
		}
		int minIndex = findMinIndex(distTo);
		
		path = toArray(paths[minIndex]);
	}

	private double relax(int adj, int i, double distTo, Stack<Integer> path,
			double[][] ea,double nextDisTo) {
		if(nextDisTo > distTo+ea[i+1][adj]){
			nextDisTo = distTo+ea[i+1][adj];
			if(path.size() == i+2){
				path.pop();
			}
			path.push(adj);
		}
		return nextDisTo;
	}

	private int[] getadj( int w, int i, int p) {
		if(p == 0){
			return new int[]{p,p+1};
		}else if(p == w-1){
			return new int[]{p-1,p};
		}
		return new int[]{p-1,p,p+1};
	}

	private int[] toArray(Stack<Integer> stack) {
		int[] path = new int[stack.size()];
		Iterator<Integer> iterator = stack.iterator();
		int i = path.length;
		while(iterator.hasNext()){
			path[--i] = iterator.next();
		}
		return path;
	}

	private int findMinIndex(double... d) {
		int min = 0;
		
		for(int i = 1; i<d.length;i++){
			if(d[i] < d[min]){
				min = i;
			}
		}
		return min;
	}
	
	public int[] seam(){
		return path;
	}
}
