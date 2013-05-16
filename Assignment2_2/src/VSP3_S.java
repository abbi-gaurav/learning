import java.util.Arrays;


public class VSP3_S {

	private int[] path;

	public VSP3_S(double[][] ea) {
		int w = ea[0].length;
		int h = ea.length;
		
		int[][] paths = new int[w][h];
		for(int i =0; i < w;i++){
			paths[i][0] = i;
		}
		
		double[] distTo = new double[w];
		for(int j = 0; j<w;j++){
			distTo[j] = ea[0][j];
		}
		
		double[] distToNext = new double[w];
		for(int j =0; j<w;j++){
			distToNext[j] = Double.POSITIVE_INFINITY;
		}
		
		for(int i = 0; i< h-1;i++){
			for(int j = 0; j<w;j++){
				for(int adj:Utils.getadj(w, paths[j][i])){
					distToNext[j] = relax(adj, i,distToNext[j], distTo[j],paths[j], ea,j );
				} 
			}
			System.arraycopy(distToNext, 0, distTo, 0, distTo.length);
			Arrays.fill(distToNext, Double.POSITIVE_INFINITY);
		}
		int minIndex = findMinIndex(distTo);
		Arrays.sort(distTo);
		System.out.println("After sort:"+distTo[0]);
		path = paths[minIndex];
	}

	private double relax(int adj, int i, double distToNextValue, double disToCUrrent, int[] path,double[][] ea, int j) {
		if(distToNextValue > disToCUrrent+ea[i+1][adj]){
			distToNextValue = disToCUrrent+ea[i+1][adj];
			path[i+1] = adj;
		}
		return distToNextValue;
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
