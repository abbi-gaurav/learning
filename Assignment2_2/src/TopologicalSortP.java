
public class TopologicalSortP {
	private boolean[][] marked;
	private Stack<Integer> stack = new Stack<Integer>();
	
	public TopologicalSortP(int w, int h,int p) {
		marked = new boolean[h][w];
		dfs(0, p, h, w);
	}


	private void dfs(int i, int j, int h, int w) {
		marked[i][j] = true;
		if(i == h-1){
			stack.push(w*i+j);
			return;
		}
		for(int adj:Utils.getadj(w, j)){
			if(!marked[i+1][adj]){
				dfs(i+1, adj, h, w);
			}
		}
		stack.push(w*i+j);
	}
	
	public static void main(String[] args) {
		System.out.println(new TopologicalSortP(3, 7,0).stack);
	}


	public Iterable<Integer> order() {
		return stack;
	}
}
