import java.util.Arrays;


public class VSP322 {
	private int[] path;
	private double[][] ea;

	public VSP322(double[][] ea) {
		int w = ea[0].length;
		int h = ea.length;
		this.ea = ea;
		
		double[] disto = new double[w];
		int[][] paths = new int[w][h];
		
		for(int i=0; i<w;i++){
			TopologicalSortP tp = new TopologicalSortP(w, h, i);
			
			double[] spDist = new double[h];
			spDist[0] = ea[0][i];
			Arrays.fill(spDist, 1, spDist.length, Double.POSITIVE_INFINITY);
			paths[i][0] = i;
			for(int o:tp.order()){
				int x = o/w;
				int y = o%w;
				if(x < h-1){
					for(int adj:Utils.getadj(w, y)){
						relax(spDist,x+1,adj,paths[i]);
					}
				}
			}
			disto[i] = spDist[h-1];
		}
		
		int minIndex = Utils.findMinIndex(disto);
		
		path = paths[minIndex];
	}

	private void relax(double[] spDist, int i, int j, int[] path) {
		if(spDist[i] > spDist[i-1]+ea[i][j]){
			if(!(Math.abs(path[i-1]-j) > 1)){
				spDist[i] = spDist[i-1]+ea[i][j];
				path[i] = j;
			}
		}
	}

	public int[] seam(){
		return path;
	}
}
